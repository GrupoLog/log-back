package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.AverageTicketDto;
import com.cesar.bd_project.dto.MonthlyRequestDto;
import com.cesar.bd_project.dto.PendingRequestsPercentageDto;
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
        String SQL = "SELECT * FROM Solicitacoes";
        List<RequestModel> requestList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                RequestModel request = new RequestModel();
                request.setIdSolicitacao(rs.getInt("id_solicitacao"));
                request.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                request.setFormaPagamento(rs.getString("forma_pagamento"));
                request.setValorPagamento(rs.getDouble("valor_pagamento"));
                request.setStatusPagamento(rs.getString("status_pagamento"));
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
        String SQL = """
                     INSERT INTO Solicitacoes (data_solicitacao, forma_pagamento, valor_pagamento, status_pagamento, clientes_cpf, id_servico)
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(request.getDataSolicitacao()));
            stmt.setString(2, request.getFormaPagamento());
            stmt.setDouble(3, request.getValorPagamento());
            stmt.setString(4, request.getStatusPagamento());
            stmt.setString(5, request.getClientesCpf());
            stmt.setInt(6, request.getIdServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar solicitação: " + e.getMessage(), e);
        }
    }

    public RequestModel findById(int id) {
        String SQL = "SELECT * FROM Solicitacoes WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                RequestModel request = new RequestModel();
                request.setIdSolicitacao(rs.getInt("id_solicitacao"));
                request.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                request.setFormaPagamento(rs.getString("forma_pagamento"));
                request.setValorPagamento(rs.getDouble("valor_pagamento"));
                request.setStatusPagamento(rs.getString("status_pagamento"));
                request.setClientesCpf(rs.getString("clientes_cpf"));
                request.setIdServico(rs.getInt("id_servico"));

                return request;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar solicitação por ID: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean atualizar(int id, RequestModel solicitacaoAtualizada) {
        String SQL = "UPDATE Solicitacoes SET data_solicitacao = ?, forma_pagamento = ?, valor_pagamento = ?, status_pagamento = ?, clientes_cpf = ?, id_servico = ? " +
                     "WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setDate(1, Date.valueOf(solicitacaoAtualizada.getDataSolicitacao()));
            stmt.setString(2, solicitacaoAtualizada.getFormaPagamento());
            stmt.setDouble(3, solicitacaoAtualizada.getValorPagamento());
            stmt.setString(4, solicitacaoAtualizada.getStatusPagamento());
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
        String SQL = "DELETE FROM Solicitacoes WHERE id_solicitacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação: " + e.getMessage(), e);
        }
    }

    public List<RevenueByPaymentKind> calcularReceitaPorFormaPagamento() {
    String SQL = """
        SELECT forma_pagamento, SUM(valor_pagamento) AS receita
        FROM Solicitacoes
        GROUP BY forma_pagamento
    """;

    List<RevenueByPaymentKind> resultado = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL);
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
        String SQL = """
            SELECT SUM(valor_pagamento) AS receita_total
            FROM Solicitacoes
            WHERE YEAR(data_solicitacao) = ?
        """;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
    
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
    String SQL = """
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
         PreparedStatement stmt = conn.prepareStatement(SQL)) {

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
        String SQL = """
            SELECT c.nome, COUNT(*) AS total_solicitacoes
            FROM Solicitacoes s
            JOIN Clientes c ON s.clientes_cpf = c.cpf
            GROUP BY c.nome
            ORDER BY total_solicitacoes DESC
            LIMIT 5;
        """;
    
        List<TopClientsByRequestsDto> resultado = new ArrayList<>();
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                resultado.add(new TopClientsByRequestsDto(
                    rs.getString("nome"),
                    rs.getInt("total_solicitacoes")
                ));
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes com mais solicitações: " + e.getMessage(), e);
        }
    
        return resultado;
    }

    public int contarTotalSolicitacoes() {
        String SQL = "SELECT COUNT(*) AS total FROM Solicitacoes";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {
    
            if (rs.next()) {
                return rs.getInt("total");
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar solicitações: " + e.getMessage(), e);
        }
    
        return 0;
    }

    public AverageTicketDto calcularTicketMedio() {
    String SQL = """
        SELECT
            SUM(s.valor_pagamento) / COUNT(DISTINCT s.clientes_cpf) AS ticket_medio
        FROM Solicitacoes s;
    """;

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            double ticketMedio = rs.getDouble("ticket_medio");
            return new AverageTicketDto(ticketMedio);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao calcular ticket médio: " + e.getMessage(), e);
    }

        return new AverageTicketDto(0.0);
    }

    public PendingRequestsPercentageDto calcularPercentualPendentes() {
    String SQL = """
        SELECT
            (SUM(CASE WHEN s.status_pagamento = 'pendente' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS percentual_pendentes
        FROM Solicitacoes s;
    """;

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            double percentual = rs.getDouble("percentual_pendentes");
            return new PendingRequestsPercentageDto(percentual);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao calcular percentual de solicitações pendentes: " + e.getMessage(), e);
    }

        return new PendingRequestsPercentageDto(0.0);
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