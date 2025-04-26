package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.VanModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VanDao implements GenericDao<VanModel, String>{

    @Override
    public List<VanModel> list() {

        List<VanModel> vanList = new ArrayList<>();
        String SQL = """
                SELECT v.chassi, v.proprietario, v.placa, m.cap_passageiros
                FROM veiculo v
                JOIN van m ON v.chassi = m.veiculo_chassi
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()){
                VanModel van = new VanModel();
                van.setChassi(rs.getString("chassi"));
                van.setProprietario(rs.getString("proprietario"));
                van.setPlaca(rs.getNString("placa"));
                van.setCapacidadePassageiros(rs.getInt("cap_passageiros"));
                vanList.add(van);
                System.out.println("Van inserida com sucesso!");

            }

            System.out.println("Vans listadas com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Vans: " + e.getMessage(), e);
        }
        return vanList;
    }

    @Override
    public VanModel findById(String chassi) {

        String SQL = """
                SELECT v.chassi, v.proprietario, v.placa, m.cap_passageiros
                FROM veiculo v
                JOIN van m ON v.chassi = m.veiculo_chassi
                WHERE v.chassi = ?
                """;
        VanModel van = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)){

            stmt.setString(1, chassi);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                van = new VanModel();
                van.setChassi(rs.getString("chassi"));
                van.setProprietario(rs.getString("proprietario"));
                van.setPlaca(rs.getNString("placa"));
                van.setCapacidadePassageiros(rs.getInt("cap_passageiros"));
                System.out.println("Van encontrada!");
            } else {
                System.out.println("Van não encontrada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar van: " + e.getMessage(), e);
        }
        return van;
    }

    @Override
    public void save(VanModel van) {

        String SQL = "INSERT INTO van(veiculo_chassi, cap_passageiros) VALUES (?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, van.getChassi());
            stmt.setInt(2, van.getCapacidadePassageiros());
            stmt.executeUpdate();
            System.out.println("Van inserida com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar van no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(VanModel van) {

        String SQL = "UPDATE van SET cap_passageiros = ? WHERE veiculo_chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, van.getCapacidadePassageiros());
            stmt.setString(2, van.getChassi());
            stmt.executeUpdate();
            System.out.println("Van atualizada com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar van: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String chassi) {

        String SQL = "DELETE FROM van WHERE veiculo_chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            stmt.executeUpdate();
            System.out.println("Van deletada com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar van: " + e.getMessage(), e);
        }
    }

}