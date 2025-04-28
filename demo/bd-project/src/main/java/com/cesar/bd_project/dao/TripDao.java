package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public void save(TripModel trip) {

        String SQL = """
                INSERT INTO viagem (data_viagem, hora_viagem, origem, destino, veiculo_chassi, motoristas_cnh)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setDate(1, java.sql.Date.valueOf(trip.getDataViagem()));
            stmt.setTime(2, java.sql.Time.valueOf(trip.getHoraViagem()));
            stmt.setString(3, trip.getOrigem());
            stmt.setString(4, trip.getDestino());
            stmt.setString(5, trip.getVeiculoChassi());
            stmt.setString(6, trip.getMotoristasCnh());
            stmt.executeUpdate();
            System.out.println("Viagem inserida com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar viagem no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(TripModel tripModel) {

    }

    //Não faz sentido ter
    @Override
    public void delete(Integer id) {

    }
}
