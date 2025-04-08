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
    public void salvar(ClientModel client) throws SQLException {
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

    // Outros métodos como atualizar, deletar, buscarPorId podem ser implementados aqui.
}