package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDao implements GenericDao<TripModel, String>{
    @Override
    public List<TripModel> list() {
        List<TripModel> tripList = new ArrayList<>();
        String SQL = "SELECT * FROM viagem";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                TripModel trip = new TripModel();
                trip.setIdViagem(rs.getInt("id_viagem"));
                trip.setDataViagem(rs.getDate("data_viagem").toLocalDate());
                trip.setHoraViagem(rs.getTime("hora_viagem").toLocalTime());
                trip.setOrigem(rs.getString("origem"));
                trip.setDestino(rs.getString("destino"));
                trip.setVeiculoChassi(rs.getString("veiculo_chassi"));
                trip.setMotoristasCnh(rs.getString("Motoristas_cnh"));
                tripList.add(trip);
            }

            System.out.println("Viagens listadas com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
        return tripList;
    }

    @Override
    public TripModel findById(String s) {
        return null;
    }

    @Override
    public void save(TripModel tripModel) {

    }

    @Override
    public void update(TripModel tripModel) {

    }

    //Não faz sentido ter
    @Override
    public void delete(String s) {

    }
}
