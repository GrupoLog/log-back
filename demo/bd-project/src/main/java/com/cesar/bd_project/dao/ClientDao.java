package com.cesar.bd_project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.cesar.bd_project.dto.ClientWithCadastroDto;
import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.utils.ConnectionFactory;

@Repository
public class ClientDao implements GenericDao<ClientModel, String> {

    @Override
    public List<ClientModel> list() {

        List<ClientModel> clientList = new ArrayList<>();
        String SQL = "SELECT * FROM Clientes c";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                ClientModel client = new ClientModel();
                client.setCpf(rs.getString("CPF"));
                client.setNome(rs.getString("nome"));
                client.setSobrenome(rs.getString("sobrenome"));
                client.setRua(rs.getString("rua"));
                client.setBairro(rs.getString("bairro"));
                client.setNumero(rs.getInt("numero"));
                client.setCidade(rs.getString("cidade"));
                clientList.add(client);
            }

            System.out.println("Clientes listados com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
        return clientList;
    }

    @Override
    public ClientModel findById(String cpf) {

        String SQL = "SELECT * FROM Clientes WHERE CPF = ?";
        ClientModel client = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
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
                System.out.println("Cliente encontrado!");
            } else {
                System.out.println("Cliente não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por CPF: " + e.getMessage(), e);
        }
        return client;
    }

    public List<ClientWithCadastroDto> listClientsWithDataCadastro() {
    List<ClientWithCadastroDto> clientList = new ArrayList<>();

    String SQL = """
        SELECT l.cpf, l.nome, l.data_cadastro
        FROM Log_Clientes l
    """;

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String cpf = rs.getString("cpf");
            String nome = rs.getString("nome");
            Date data = rs.getDate("data_cadastro");

            ClientWithCadastroDto dto = new ClientWithCadastroDto();
            dto.setCpf(cpf);
            dto.setNome(nome);
            if (data != null) {
                dto.setDataCadastro(((java.sql.Date) data).toLocalDate());
            }


            clientList.add(dto);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar clientes com data de cadastro: " + e.getMessage(), e);
    }

        return clientList;
    }


    @Override
    public void save(ClientModel client) {

        String SQL = "INSERT INTO Clientes (CPF, nome, sobrenome, rua, bairro, numero, cidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, client.getCpf());
            stmt.setString(2, client.getNome());
            stmt.setString(3, client.getSobrenome());
            stmt.setString(4, client.getRua());
            stmt.setString(5, client.getBairro());
            stmt.setInt(6, client.getNumero());
            stmt.setString(7, client.getCidade());
            stmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente no banco de dados: " + e.getMessage(), e);
        }
    }


    @Override
    public void update(ClientModel client) {

        String SQL = "UPDATE Clientes SET nome = ?, sobrenome = ?, rua = ?, bairro = ?, numero = ?, cidade = ? WHERE CPF = ?";

        // Atualiza os dados do cliente
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, client.getNome());
            stmt.setString(2, client.getSobrenome());
            stmt.setString(3, client.getRua());
            stmt.setString(4, client.getBairro());
            stmt.setInt(5, client.getNumero());
            stmt.setString(6, client.getCidade());
            stmt.setString(7, client.getCpf());
            stmt.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }


    @Override
    public void delete(String cpf) {

        String SQL = "DELETE FROM Clientes WHERE CPF = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }
}