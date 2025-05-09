package com.cesar.bd_project.dao;

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

    public void save(RequestModel solicitacao) {
        String sql = "INSERT INTO Solicitacoes (id_solicitacao, data_solicitacao, forma_pagamento, valor_pagamento, clientes_cpf, id_servico) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitacao.getIdSolicitacao());
            stmt.setDate(2, Date.valueOf(solicitacao.getDataSolicitacao()));
            stmt.setString(3, solicitacao.getFormaPagamento());
            stmt.setDouble(4, solicitacao.getValorPagamento());
            stmt.setString(5, solicitacao.getClientesCpf());
            stmt.setInt(6, solicitacao.getIdServico());

            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente (se aplicável)
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                solicitacao.setIdSolicitacao(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar solicitação: " + e.getMessage(), e);
        }
    }

    public RequestModel buscarPorId(int id) {
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