package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.DriverModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverDao implements GenericDao<DriverModel, String>  {
    @Override
    public List<DriverModel> list() {

        List<DriverModel> driverList = new ArrayList<>();
        String SQL = "SELECT * FROM motoristas";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while(rs.next()) {
                DriverModel driver = new DriverModel();
                driver.setCnh(rs.getString("cnh"));
                driver.setTipoCnh(rs.getString("tipo_cnh"));
                driver.setCpf(rs.getString("cpf"));
                driver.setNome(rs.getString("nome"));
                driver.setTipo(rs.getString("tipo"));
                driver.setTelefoneUm(rs.getString("telefone_um"));
                driver.setTelefoneDois(rs.getString("telefone_dois"));
                driver.setCnhSupervisionado(rs.getString("cnh_supervisionado"));
                driverList.add(driver);
            }

            System.out.println("Motoristas listados com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }
        return driverList;
    }

    @Override
    public DriverModel findById(String cnh) {

        String SQL = "SELECT * FROM motoristas WHERE cnh = ?";
        DriverModel driver = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, cnh);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                driver = new DriverModel();
                driver.setCnh(rs.getString("cnh"));
                driver.setTipoCnh(rs.getString("tipo_cnh"));
                driver.setNome(rs.getString("cpf"));
                driver.setTipo(rs.getString("tipo"));
                driver.setTelefoneUm(rs.getString("telefone_um"));
                driver.setTelefoneDois(rs.getString("telefone_dois"));
                driver.setCnhSupervisionado(rs.getString("cnh_supervisionado"));
            }else {
                System.out.println("Motorista não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motorista: " + e.getMessage(), e);
        }
        return driver;
    }

    public DriverModel findByCpf(String cpf) {

        String SQL = "SELECT * FROM motoristas WHERE cpf = ?";
        DriverModel driver = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                driver = new DriverModel();
                driver.setCnh(rs.getString("cnh"));
                driver.setTipoCnh(rs.getString("tipo_cnh"));
                driver.setNome(rs.getString("cpf"));
                driver.setTipo(rs.getString("tipo"));
                driver.setTelefoneUm(rs.getString("telefone_um"));
                driver.setTelefoneDois(rs.getString("telefone_dois"));
                driver.setCnhSupervisionado(rs.getString("cnh_supervisionado"));
            }else {
                System.out.println("Motorista não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motorista: " + e.getMessage(), e);
        }
        return driver;
    }

    @Override
    public void save(DriverModel driver) {

        String SQL = """
                INSERT INTO motoristas (cnh, tipo_cnh, cpf, nome, tipo, telefone_um, telefone_dois, cnh_supervisionado) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, driver.getCnh());
            stmt.setString(2, driver.getTipoCnh());
            stmt.setString(3, driver.getCpf());
            stmt.setString(4, driver.getNome());
            stmt.setString(5, driver.getTipo());
            stmt.setString(6, driver.getTelefoneUm());
            stmt.setString(7, driver.getTelefoneDois());
            stmt.setString(8, driver.getCnhSupervisionado());
            stmt.executeUpdate();
            System.out.println("Motorista inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar motorista no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(DriverModel driverModel) {

    }
    // Não faz sentido ter

    @Override
    public void delete(String s) {

    }
}
