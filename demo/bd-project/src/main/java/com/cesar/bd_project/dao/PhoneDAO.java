package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.PhoneModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneDAO implements GenericDao<PhoneModel, String>{
    @Override
    public List<PhoneModel> list() throws SQLException {

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
    public PhoneModel save(PhoneModel phoneModel) throws SQLException {
        String SQL = "INSERT INTO Telefone (telefone, clientes_cpf) VALUES (?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, phoneModel.getTelefone());
            stmt.setString(2, phoneModel.getClientesCpf());

        }
        return null;
    }

    @Override
    public PhoneModel findById(String s) throws SQLException {
        return null;
    }

    @Override
    public void update(PhoneModel phoneModel) throws SQLException {

    }

    @Override
    public void delete(String s) throws SQLException {

    }
}
