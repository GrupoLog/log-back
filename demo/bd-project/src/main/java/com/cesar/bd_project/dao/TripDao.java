package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDao implements GenericDao<TripModel, Integer>{
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
                trip.setMotoristasCnh(rs.getString("motoristas_cnh"));
                tripList.add(trip);
            }

            System.out.println("Viagens listadas com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
        return tripList;
    }

    @Override
    public TripModel findById(Integer id) {

        String SQL = "SELECT * FROM viagem WHERE id_viagem = ?";
        TripModel trip = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trip = new TripModel();
                trip.setIdViagem(rs.getInt("id_viagem"));
                trip.setDataViagem(rs.getDate("data_viagem").toLocalDate());
                trip.setHoraViagem(rs.getTime("hora_viagem").toLocalTime());
                trip.setOrigem(rs.getString("origem"));
                trip.setDestino(rs.getString("destino"));
                trip.setVeiculoChassi(rs.getString("veiculo_chassi"));
                trip.setMotoristasCnh(rs.getString("motoristas_cnh"));
                System.out.println("Viagem encontrada!");
            } else {
                System.out.println("Viagem não encontrada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar viagem pelo ID: " + e.getMessage(), e);
        }
        return trip;
    }

    @Override
    public void save(TripModel tripModel) {

    }

    @Override
    public void update(TripModel tripModel) {

    }

    //Não faz sentido ter
    @Override
    public void delete(Integer id) {

    }
}
