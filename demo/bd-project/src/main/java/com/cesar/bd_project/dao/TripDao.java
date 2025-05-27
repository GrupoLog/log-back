package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.TotalTripDto;
import com.cesar.bd_project.dto.TripDto;
import com.cesar.bd_project.dto.TripTypeCountDto;
import com.cesar.bd_project.dto.TripWithDetailDto;
import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDao implements GenericDao<TripModel, Integer>{
    @Override
    public List<TripModel> list() {
        List<TripModel> tripList = new ArrayList<>();
        String SQL = "SELECT * FROM Viagem";

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

    public List<TripDto> listWithDetail() {
        List<TripDto> tripList = new ArrayList<>();
        String SQL = """
                SELECT v.id_viagem, v.data_viagem, v.hora_viagem, v.origem, v.destino,
                ve.placa, m.nome  
                FROM Viagem v
                JOIN Veiculo ve ON ve.chassi = v.veiculo_chassi
                JOIN Motoristas m ON m.cnh = v.motoristas_cnh
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                TripDto trip = new TripDto();
                trip.setIdViagem(rs.getInt("id_viagem"));
                trip.setDataViagem(rs.getDate("data_viagem").toLocalDate());
                trip.setHoraViagem(rs.getTime("hora_viagem").toLocalTime());
                trip.setOrigem(rs.getString("origem"));
                trip.setDestino(rs.getString("destino"));
                trip.setPlacaVeiculo(rs.getString("placa"));
                trip.setNomeMotorista(rs.getString("nome"));
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

        String SQL = "SELECT * FROM Viagem WHERE id_viagem = ?";
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

    public TripWithDetailDto findByIdWithDetail(Integer id) {

        TripWithDetailDto trip = null;
        String SQL = """
                SELECT v.id_viagem, v.data_viagem, v.hora_viagem, v.origem, v.destino,
                m.cnh, m.nome, m.tipo, m.tipo_cnh, m.telefone_um,  
                ve.placa, ve.chassi, ve.proprietario
                FROM Viagem v
                JOIN Motoristas m ON m.cnh = v.motoristas_cnh
                JOIN Veiculo ve ON ve.chassi = v.veiculo_chassi
                WHERE id_viagem = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trip = new TripWithDetailDto();
                trip.setIdViagem(rs.getInt("id_viagem"));
                trip.setDataViagem(rs.getDate("data_viagem").toLocalDate());
                trip.setHoraViagem(rs.getTime("hora_viagem").toLocalTime());
                trip.setOrigem(rs.getString("origem"));
                trip.setDestino(rs.getString("destino"));
                trip.setCnh(rs.getString("cnh"));
                trip.setNome(rs.getString("nome"));
                trip.setTipo(rs.getString("tipo"));
                trip.setTipoCnh(rs.getString("tipo_cnh"));
                trip.setTelefoneUm(rs.getString("telefone_um"));
                trip.setPlaca(rs.getString("placa"));
                trip.setChassi(rs.getString("chassi"));
                trip.setProprietario(rs.getString("proprietario"));
                System.out.println("Viagem encontrada!");
            } else {
                System.out.println("Viagem não encontrada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar viagem pelo ID: " + e.getMessage(), e);
        }
        return trip;
    }

    public TotalTripDto findTotalTrip() {
        TotalTripDto totalTrip = new TotalTripDto();
        String SQL = """
                      SELECT COUNT(*) as total_viagens
                      FROM Viagem
                      """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            if (rs.next()) {
                totalTrip.setTotalViagens(rs.getInt("total_viagens"));
            }

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
        return totalTrip;
    }

    @Override
    public void save(TripModel trip) {

        String SQL = """
                INSERT INTO Viagem (data_viagem, hora_viagem, origem, destino, veiculo_chassi, motoristas_cnh)
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
    public void update(TripModel trip) {
        String SQL = """
                UPDATE Viagem SET data_viagem = ?, hora_viagem = ?, origem = ?, destino = ?, veiculo_chassi = ?, motoristas_cnh = ?
                WHERE id_viagem = ?
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setDate(1, java.sql.Date.valueOf(trip.getDataViagem()));
            stmt.setTime(2, java.sql.Time.valueOf(trip.getHoraViagem()));
            stmt.setString(3, trip.getOrigem());
            stmt.setString(4, trip.getDestino());
            stmt.setString(5, trip.getVeiculoChassi());
            stmt.setString(6, trip.getMotoristasCnh());
            stmt.setInt(7, trip.getIdViagem());
            stmt.executeUpdate();
            System.out.println("Viagem atualizada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar viagem no banco de dados: " + e.getMessage(), e);
        }
    }

    public List<TripTypeCountDto> contarViagensPorTipo(int ano) {
        String sql = """
            CALL sp_contar_viagens_por_tipo( ? )
            
        """;
    
        List<TripTypeCountDto> resultado = new ArrayList<>();
    
            try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TripTypeCountDto dto = new TripTypeCountDto();
                dto.setTipo(rs.getString("tipo"));
                dto.setQuantidade(rs.getInt("total"));
                resultado.add(dto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar viagens por tipo e ano: " + e.getMessage(), e);
        }
    
        return resultado;
    }

    //Não faz sentido ter
    @Override
    public void delete(Integer id) {

    }
}
