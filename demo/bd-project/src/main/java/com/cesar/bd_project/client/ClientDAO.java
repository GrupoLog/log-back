package com.cesar.bd_project.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cesar.bd_project.utils.ConnectionFactory;

@Repository
public class ClientDAO {
    public ClientModel salvar(ClientModel client) throws SQLException {
        String sql = "INSERT INTO Clientes (CPF, nome, sobrenome, rua, bairro, numero, cidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, client.getCpf());
            stmt.setString(2, client.getNome());
            stmt.setString(3, client.getSobrenome());
            stmt.setString(4, client.getRua());
            stmt.setString(5, client.getBairro());
            stmt.setInt(6, client.getNumero());
            stmt.setString(7, client.getCidade());
    
            stmt.executeUpdate();
    
            return client;
        }
    }
    

    public List<ClientModel> listarTodos() throws SQLException {
        List<ClientModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClientModel client = new ClientModel();
                client.setCpf(rs.getString("CPF"));
                client.setNome(rs.getString("nome"));
                client.setSobrenome(rs.getString("sobrenome"));
                client.setRua(rs.getString("rua"));
                client.setBairro(rs.getString("bairro"));
                client.setNumero(rs.getInt("numero"));
                client.setCidade(rs.getString("cidade"));
                lista.add(client);
            }
        }

        return lista;
    }

    public ClientModel buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE CPF = ?";
        ClientModel client = null;

        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            System.err.println("CPF não pode ser nulo ou vazio");
            return null;        
        }        
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new ClientModel();
                client.setCpf(rs.getString("CPF"));
                client.setNome(rs.getString("nome"));
                client.setSobrenome(rs.getString("sobrenome"));
                client.setRua(rs.getString("rua"));
                client.setBairro(rs.getString("bairro"));
                client.setNumero(rs.getInt("numero"));
                client.setCidade(rs.getString("cidade"));
            }else {
                System.err.println("Cliente com o CPF " + cpf + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        }

        return client;
    }

    public boolean atualizar(ClientModel client) throws SQLException {
        String sql = "UPDATE Clientes SET nome = ?, sobrenome = ?, rua = ?, bairro = ?, numero = ?, cidade = ? WHERE CPF = ?";

        // Verifica se o CPF é nulo ou vazio
        if (client.getCpf() == null || client.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = buscarPorCpf(client.getCpf());
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + client.getCpf() + " não encontrado.");
        }
        
        // Atualiza os dados do cliente
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNome());
            stmt.setString(2, client.getSobrenome());
            stmt.setString(3, client.getRua());
            stmt.setString(4, client.getBairro());
            stmt.setInt(5, client.getNumero());
            stmt.setString(6, client.getCidade());
            stmt.setString(7, client.getCpf());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    public boolean deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE CPF = ?";

        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = buscarPorCpf(cpf);
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + cpf + " não encontrado.");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }

}