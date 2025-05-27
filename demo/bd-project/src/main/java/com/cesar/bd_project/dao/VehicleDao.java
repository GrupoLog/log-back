package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.*;
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

        String SQL = "SELECT * FROM Veiculo WHERE chassi = ?";
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

        String SQL = "SELECT * FROM Veiculo WHERE placa = ?";
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

        String SQL = "INSERT INTO Veiculo(chassi, proprietario, placa) VALUES (?, ?, ?)";

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

        String SQL = "DELETE FROM Veiculo WHERE chassi = ?";

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
        FROM Viagem vi
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

    public VehicleCountDto countVehicles() {
        String SQL = """
                        SELECT COUNT(*) AS total_veiculos
                        FROM Veiculo
                     """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Integer totalVeiculos = rs.getInt("total_veiculos");
                return new VehicleCountDto(totalVeiculos);
            }

            return new VehicleCountDto(0); // Return 0 if no vehicles found

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar veículos: " + e.getMessage(), e);
        }
    }

    public UnusedVehiclesCountDto countUnusedVehicles() {
        String SQL = """
                        SELECT COUNT(*) AS total_nao_utilizados
                        FROM Veiculo v
                        WHERE v.chassi NOT IN (SELECT DISTINCT veiculo_chassi FROM Viagem)
                        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Integer totalNaoUtilizados = rs.getInt("total_nao_utilizados");
                return new UnusedVehiclesCountDto(totalNaoUtilizados);
            }

            return new UnusedVehiclesCountDto(0); // Return 0 if no unused vehicles found

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar veículos não utilizados: " + e.getMessage(), e);
        }
    }

    public TerceirizadosPercentageDto getTerceirizadosPercentage() {
        String SQL = """
                        SELECT COUNT(*) AS total,
                        SUM(CASE WHEN proprietario = 'terceirizado' THEN 1 ELSE 0 END) AS total_terceirizados
                        FROM Veiculo
                     """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            double percentagem = 0.0;

            if (rs.next()) {
                int totalVeiculos = rs.getInt("total");
                int totalTerceirizados = rs.getInt("total_terceirizados");

                // Calculate the percentage
                percentagem = totalVeiculos > 0 ?
                        ((double) totalTerceirizados / totalVeiculos) * 100 : 0;
            }

            return new TerceirizadosPercentageDto(percentagem);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular percentagem de veículos terceirizados: " + e.getMessage(), e);
        }
    }

    public List<VehicleTypePercentageDto> getVehicleTypePercentagesEfficient() {

        List<VehicleTypePercentageDto> result = new ArrayList<>();
        String SQL = """
                        WITH type_counts AS (
                         SELECT 'Moto' as tipo, COUNT(*) as quantidade FROM Moto
                         UNION
                         SELECT 'Van' as tipo, COUNT(*) as quantidade FROM Van
                        ),
                        total AS (
                         SELECT COUNT(*) as total FROM Veiculo
                        )
                        SELECT tc.tipo, (tc.quantidade * 100.0 / t.total) as percentagem
                        FROM type_counts tc, total t
                        """;


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                double percentagem = rs.getDouble("percentagem");

                result.add(new VehicleTypePercentageDto(tipo, percentagem));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular percentagem de tipos de veículo: " + e.getMessage(), e);
        }
    }


    public List<VehicleTypeMonthlyTripsDto> getVehicleTypeMonthlyTrips() {

        List<VehicleTypeMonthlyTripsDto> result = new ArrayList<>();
        String SQL = """
                SELECT
                   CASE
                        WHEN m.veiculo_chassi IS NOT NULL THEN 'Moto'
                        WHEN van.veiculo_chassi IS NOT NULL THEN 'Van'
                        ELSE 'Outro'
                   END AS tipo_veiculo,
                   MONTH(v.data_viagem) AS mes, YEAR(v.data_viagem) AS ano, COUNT(*) AS quantidade_viagens
                FROM Viagem v
                JOIN Veiculo veic ON v.veiculo_chassi = veic.chassi
                LEFT JOIN Moto m ON veic.chassi = m.veiculo_chassi
                LEFT JOIN Van van ON veic.chassi = van.veiculo_chassi
                GROUP BY tipo_veiculo, YEAR(v.data_viagem), MONTH(v.data_viagem)
                ORDER BY YEAR(v.data_viagem), MONTH(v.data_viagem), tipo_veiculo
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            // Map of month numbers to names in Portuguese
            String[] monthNames = {
                    "", // Index 0 is empty since months start at 1
                    "Janeiro", "Fevereiro", "Março", "Abril",
                    "Maio", "Junho", "Julho", "Agosto",
                    "Setembro", "Outubro", "Novembro", "Dezembro"
            };

            while (rs.next()) {
                String tipoVeiculo = rs.getString("tipo_veiculo");
                Integer mes = rs.getInt("mes");
                Integer ano = rs.getInt("ano");
                Integer quantidadeViagens = rs.getInt("quantidade_viagens");

                // Get month name based on the month number
                String nomeMes = (mes >= 1 && mes <= 12) ? monthNames[mes] : "Desconhecido";

                result.add(new VehicleTypeMonthlyTripsDto(tipoVeiculo, mes, nomeMes, ano, quantidadeViagens));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter quantidade de viagens por tipo de veículo e mês: " + e.getMessage(), e);
        }
    }

}
