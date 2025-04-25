package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.MotoModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MotoDao implements GenericDao<MotoModel, String>{

    @Override
    public List<MotoModel> list() {

        List<MotoModel> motoList = new ArrayList<>();
        String SQL = """
                SELECT v.chassi, v.proprietario, v.placa, m.cap_carga
                FROM veiculo v
                JOIN moto m ON v.chassi = m.veiculo_chassi
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()){
                MotoModel moto = new MotoModel();
                moto.setChassi(rs.getString("chassi"));
                moto.setProprietario(rs.getString("proprietario"));
                moto.setPlaca(rs.getNString("placa"));
                moto.setCapacidadeCarga(rs.getInt("cap_carga"));
                motoList.add(moto);
                System.out.println("Moto inserida com sucesso!");

            }

            System.out.println("Motos listadas com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Motos: " + e.getMessage(), e);
        }
        return motoList;
    }

    @Override
    public MotoModel findById(String chassi) {

        String SQL = """
                SELECT v.chassi, v.proprietario, v.placa, m.cap_carga
                FROM veiculo v
                JOIN moto m ON v.chassi = m.veiculo_chassi
                WHERE v.chassi = ?
                """;
        MotoModel moto = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)){

            stmt.setString(1, chassi);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                moto = new MotoModel();
                moto.setChassi(rs.getString("chassi"));
                moto.setProprietario(rs.getString("proprietario"));
                moto.setPlaca(rs.getNString("placa"));
                moto.setCapacidadeCarga(rs.getInt("cap_carga"));
                System.out.println("Moto encontrada!");
            } else {
                System.out.println("Moto não encontrada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar moto: " + e.getMessage(), e);
        }
        return moto;
    }

    @Override
    public void save(MotoModel moto) {

        String SQL = "INSERT INTO moto(veiculo_chassi, cap_carga) VALUES (?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, moto.getChassi());
            stmt.setInt(2, moto.getCapacidadeCarga());
            stmt.executeUpdate();
            System.out.println("Moto inserida com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar moto no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(MotoModel moto) {

        String SQL = "UPDATE moto SET cap_carga = ? WHERE veiculo_chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, moto.getCapacidadeCarga());
            stmt.setString(2, moto.getChassi());
            stmt.executeUpdate();
            System.out.println("Moto atualizada com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar moto: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String chassi) {

        String SQL = "DELETE FROM moto WHERE veiculo_chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            stmt.executeUpdate();
            System.out.println("Moto deletada com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar moto: " + e.getMessage(), e);
        }
    }

}