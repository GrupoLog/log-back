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
    public List<MotoModel> list() throws SQLException {
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
            }

        }
        return motoList;
    }

    @Override
    public MotoModel findById(String chassi) throws SQLException {
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
            }
        }
        return moto;
    }

    @Override
    public MotoModel save(MotoModel motoModel) throws SQLException {
        return null;
    }

    @Override
    public void update(MotoModel motoModel) throws SQLException {

    }

    @Override
    public void delete(String chassi) throws SQLException {
        String SQL = "DELETE FROM moto WHERE veiculo_chassi = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            stmt.executeUpdate();
        }

    }
}
