package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.MonthlyRequestDto;
import com.cesar.bd_project.dto.RevenueByPaymentKind;
import com.cesar.bd_project.dto.TopClientsByRequestsDto;
import com.cesar.bd_project.dto.TotalRequestsDto;
import com.cesar.bd_project.model.RequestModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao implements GenericDao<RequestModel, Integer>{

    public List<RequestModel> list() {
        String sql = "SELECT * FROM Solicitacoes";
        List<RequestModel> requestList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RequestModel request = new RequestModel();
                request.setIdSolicitacao(rs.getInt("id_solicitacao"));
                request.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                request.setFormaPagamento(rs.getString("forma_pagamento"));
                request.setValorPagamento(rs.getDouble("valor_pagamento"));
                request.setClientesCpf(rs.getString("clientes_cpf"));
                request.setIdServico(rs.getInt("id_servico"));

                requestList.add(request);
            }

            System.out.println("Solicitacoes listadas com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações: " + e.getMessage(), e);
        }

        return requestList;
    }

    public void save(RequestModel request) {
        String sql = "INSERT INTO Solicitacoes (data_solicitacao, forma_pagamento, valor_pagamento, clientes_cpf, id_servico) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(request.getDataSolicitacao()));
            stmt.setString(2, request.getFormaPagamento());
            stmt.setDouble(3, request.getValorPagamento());
            stmt.setString(4, request.getClientesCpf());
            stmt.setInt(5, request.getIdServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar solicitação: " + e.getMessage(), e);
        }
    }

    public RequestModel findById(int id) {
        String sql = "SELECT * FROM Solicitacoes WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                RequestModel solicitacao = new RequestModel();
                solicitacao.setIdSolicitacao(rs.getInt("id_solicitacao"));
                solicitacao.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                solicitacao.setFormaPagamento(rs.getString("forma_pagamento"));
                solicitacao.setValorPagamento(rs.getDouble("valor_pagamento"));
                solicitacao.setClientesCpf(rs.getString("clientes_cpf"));
                solicitacao.setIdServico(rs.getInt("id_servico"));

                return solicitacao;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar solicitação por ID: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean atualizar(int id, RequestModel solicitacaoAtualizada) {
        String sql = "UPDATE Solicitacoes SET data_solicitacao = ?, forma_pagamento = ?, valor_pagamento = ?, clientes_cpf = ?, id_servico = ? " +
                     "WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(solicitacaoAtualizada.getDataSolicitacao()));
            stmt.setString(2, solicitacaoAtualizada.getFormaPagamento());
            stmt.setDouble(3, solicitacaoAtualizada.getValorPagamento());
            stmt.setString(5, solicitacaoAtualizada.getClientesCpf());
            stmt.setInt(6, solicitacaoAtualizada.getIdServico());
            stmt.setInt(7, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar solicitação: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM Solicitacoes WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação: " + e.getMessage(), e);
        }
    }

    public List<RevenueByPaymentKind> calcularReceitaPorFormaPagamento() {
    String sql = """
        SELECT forma_pagamento, SUM(valor_pagamento) AS receita
        FROM Solicitacoes
        GROUP BY forma_pagamento
    """;

    List<RevenueByPaymentKind> resultado = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String formaPagamento = rs.getString("forma_pagamento");
            Double receita = rs.getDouble("receita");
            resultado.add(new RevenueByPaymentKind(formaPagamento, receita));
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao calcular receita por forma de pagamento: " + e.getMessage(), e);
    }

        return resultado;
    }   

    public Double calcularReceitaTotalPorAno(int ano) {
        String sql = """
            SELECT SUM(valor_pagamento) AS receita_total
            FROM Solicitacoes
            WHERE YEAR(data_solicitacao) = ?
        """;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, ano);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getDouble("receita_total");
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular receita total por ano: " + e.getMessage(), e);
        }
    
        return 0.0; 
    }

    public List<MonthlyRequestDto> contarSolicitacoesPorMes(int ano) {
    String sql = """
        SELECT 
            DATE_FORMAT(v.data_viagem, '%Y-%m') AS mes,
            COUNT(s.id_servico) AS total
        FROM Servicos s
        JOIN viagem v ON s.id_viagem = v.id_viagem
        WHERE YEAR(v.data_viagem) = ?
        GROUP BY mes
        ORDER BY mes;
    """;

    List<MonthlyRequestDto> resultado = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, ano);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            resultado.add(new MonthlyRequestDto(
                rs.getString("mes"),
                rs.getInt("total")
            ));
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao consultar solicitações por mês: " + e.getMessage(), e);
    }

        return resultado;
    }

    public List<TopClientsByRequestsDto> buscarClientesComMaisSolicitacoes() {
        String sql = """
            SELECT s.clientes_cpf, COUNT(*) AS total_solicitacoes
            FROM Solicitacoes s
            GROUP BY s.clientes_cpf
            ORDER BY total_solicitacoes DESC
            LIMIT 5;
        """;
    
        List<TopClientsByRequestsDto> resultado = new ArrayList<>();
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                resultado.add(new TopClientsByRequestsDto(
                    rs.getString("clientes_cpf"),
                    rs.getInt("total_solicitacoes")
                ));
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes com mais solicitações: " + e.getMessage(), e);
        }
    
        return resultado;
    }

    public int contarTotalSolicitacoes() {
        String sql = "SELECT COUNT(*) AS total FROM Solicitacoes";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            if (rs.next()) {
                return rs.getInt("total");
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar solicitações: " + e.getMessage(), e);
        }
    
        return 0;
    }
      

    @Override
    public RequestModel findById(Integer integer) {
        return null;
    }


    @Override
    public void update(RequestModel requestModel) {

    }

    @Override
    public void delete(Integer integer) {

    }
}