package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.PhoneModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneDao implements GenericDao<PhoneModel, String>{
    @Override
    public List<PhoneModel> list() {

        List<PhoneModel> phoneList = new ArrayList<>();
        String SQL = "SELECT * FROM Telefone";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while(rs.next()) {
                PhoneModel phone = new PhoneModel();
                phone.setTelefone(rs.getString("telefone"));
                phone.setClientesCpf(rs.getString("clientes_cpf"));
                phoneList.add(phone);
            }
        }
        return phoneList;
    }

    @Override
    public PhoneModel save(PhoneModel phone) {
        String SQL = "INSERT INTO Telefone (telefone, clientes_cpf) VALUES (?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, phone.getTelefone());
            stmt.setString(2, phone.getClientesCpf());
            stmt.executeUpdate();

        }
        return phone;
    }

    @Override
    public PhoneModel findById(String cellphone) {
        String SQL = "SELECT * FROM Telefone WHERE telefone = ?";
        PhoneModel phone = null;
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, cellphone);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                phone = new PhoneModel();
                phone.setTelefone(rs.getString("telefone"));
                phone.setClientesCpf(rs.getString("clientes_cpf"));
            }
        }
        return phone;
    }


    public List<PhoneModel> findByCpf(String cpf) throws SQLException {
        String SQL = "SELECT * FROM Telefone WHERE clientes_cpf = ?";
        List<PhoneModel> phoneList = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                PhoneModel phone = new PhoneModel();
                phone.setTelefone(rs.getString("telefone"));
                phone.setClientesCpf(rs.getString("clientes_cpf"));
                phoneList.add(phone);
            }

        }
        return phoneList;
    }

    @Override
    public void update(PhoneModel phoneModel) {
        String SQL = "UPDATE Telefone SET telefone = ? WHERE clientes_cpf = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, phoneModel.getTelefone());
            stmt.setString(2, phoneModel.getClientesCpf());
            stmt.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar telefone: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String cellphone) {
        String SQL = "DELETE FROM Telefone WHERE telefone = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cellphone);
            stmt.executeUpdate();
        }
    }
}
