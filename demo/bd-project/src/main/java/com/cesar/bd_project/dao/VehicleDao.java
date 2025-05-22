package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.MostUsedVehicleDto;
import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDao implements GenericDao<VehicleModel, String> {

    @Override
    public List<VehicleModel> list() {

        List<VehicleModel> vehicleList = new ArrayList<>();
        String SQL = "SELECT * FROM Veiculo";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                VehicleModel vehicle = new VehicleModel();
                vehicle.setChassi(rs.getString("chassi"));
                vehicle.setProprietario(rs.getString("proprietario"));
                vehicle.setPlaca(rs.getString("placa"));
                vehicleList.add(vehicle);
            }

            System.out.println("Veiculos listados com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage(), e);
        }
        return vehicleList;
    }

    @Override
    public VehicleModel findById(String chassi) {

        String SQL = "SELECT * FROM veiculo WHERE chassi = ?";
        VehicleModel vehicle = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                vehicle = new VehicleModel();
                vehicle.setChassi(rs.getString("chassi"));
                vehicle.setProprietario(rs.getString("proprietario"));
                vehicle.setPlaca(rs.getString("placa"));
                System.out.println("Veiculo encontrado!");
            } else {
                System.out.println("Veiculo não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo: " + e.getMessage(), e);
        }
        return vehicle;
    }

    public VehicleModel findByPlate(String plate) {

        String SQL = "SELECT * FROM veiculo WHERE placa = ?";
        VehicleModel vehicle = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, plate);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                vehicle = new VehicleModel();
                vehicle.setChassi(rs.getString("chassi"));
                vehicle.setProprietario(rs.getString("proprietario"));
                vehicle.setPlaca(rs.getString("placa"));
                System.out.println("Veiculo encontrado!");
            } else {
                System.out.println("Veiculo não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo: " + e.getMessage(), e);
        }
        return vehicle;
    }

    @Override
    public void save(VehicleModel vehicle) {

        String SQL = "INSERT INTO veiculo(chassi, proprietario, placa) VALUES (?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, vehicle.getChassi());
            stmt.setString(2, vehicle.getProprietario());
            stmt.setString(3, vehicle.getPlaca());
            stmt.executeUpdate();
            System.out.println("Veiculo inserido com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veiculo no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(VehicleModel vehicleModel) {
        // Chassi is not updatable
        String SQL = "UPDATE Veiculo SET proprietario = ?, placa = ? WHERE chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, vehicleModel.getProprietario());
            stmt.setString(2, vehicleModel.getPlaca());
            stmt.setString(3, vehicleModel.getChassi());
            stmt.executeUpdate();
            System.out.println("Veiculo atualizado com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veiculo: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String chassi) {

        String SQL = "DELETE FROM veiculo WHERE chassi = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            stmt.executeUpdate();System.out.println("Veiculo deletado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veiculo: " + e.getMessage(), e);
        }
    }

    public List<MostUsedVehicleDto> listarVeiculosMaisUsados() {
    String sql = """
        SELECT
            v.chassi,
            v.placa,
            COUNT(*) AS total_viagens
        FROM viagem vi
        JOIN Veiculo v ON vi.veiculo_chassi = v.chassi
        GROUP BY v.chassi, v.placa
        ORDER BY total_viagens DESC
        LIMIT 5;
    """;

    List<MostUsedVehicleDto> resultado = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            MostUsedVehicleDto dto = new MostUsedVehicleDto();
            dto.setChassi(rs.getString("chassi"));
            dto.setPlaca(rs.getString("placa"));
            dto.setTotalViagens(rs.getInt("total_viagens"));
            resultado.add(dto);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar veículos mais utilizados: " + e.getMessage(), e);
    }

        return resultado;
    }

}
