package com.cesar.bd_project.dao;

import com.cesar.bd_project.dto.DriverTripCountDto;
import com.cesar.bd_project.dto.DriverWithDestinationsDto;
import com.cesar.bd_project.dto.TotalDriverByTypeDto;
import com.cesar.bd_project.model.DriverModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DriverDao implements GenericDao<DriverModel, String>  {
    @Override
    public List<DriverModel> list() {

        List<DriverModel> driverList = new ArrayList<>();
        String SQL = "SELECT * FROM Motoristas";

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

        String SQL = "SELECT * FROM Motoristas WHERE cnh = ?";
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
                System.out.println("Motorista não encontrado por CNH!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motorista: " + e.getMessage(), e);
        }
        return driver;
    }

    public DriverModel findByCpf(String cpf) {

        String SQL = "SELECT * FROM Motoristas WHERE cpf = ?";
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
                System.out.println("Motorista não encontrado por CPF!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motorista: " + e.getMessage(), e);
        }
        return driver;
    }

    @Override
    public void save(DriverModel driver) {

        String SQL = """
                INSERT INTO Motoristas (cnh, tipo_cnh, cpf, nome, tipo, telefone_um, telefone_dois, cnh_supervisionado) 
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
    public void update(DriverModel driver) {
        String SQL = """
                UPDATE Motoristas SET tipo_cnh = ?, nome = ?, tipo = ?, telefone_um = ?, telefone_dois = ?, cnh_supervisionado = ? 
                WHERE cnh = ?
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, driver.getTipoCnh());
            stmt.setString(2, driver.getNome());
            stmt.setString(3, driver.getTipo());
            stmt.setString(4, driver.getTelefoneUm());
            stmt.setString(5, driver.getTelefoneDois());
            stmt.setString(6, driver.getCnhSupervisionado());
            stmt.setString(7, driver.getCnh());
            stmt.executeUpdate();
            System.out.println("Motorista atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista no banco de dados: " + e.getMessage(), e);
        }
    }

    public List<TotalDriverByTypeDto> countDriversByType() {
        List<TotalDriverByTypeDto> result = new ArrayList<>();
        String SQL = """
                    SELECT tipo AS tipo_motorista, COUNT(*) AS total_motoristas
                    FROM Motoristas
                    GROUP BY tipo
                    """;

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                String tipoMotorista = rs.getString("tipo_motorista");
                Integer totalMotoristas = rs.getInt("total_motoristas");

                result.add(new TotalDriverByTypeDto(tipoMotorista, totalMotoristas));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar motoristas por tipo: " + e.getMessage(), e);
        }
    }

    public List<DriverTripCountDto> countTripsByDriver() {
        List<DriverTripCountDto> result = new ArrayList<>();
        String SQL = """
                    SELECT m.nome AS nome_motorista, COUNT(v.id_viagem) AS total_viagens
                    FROM Motoristas m
                    LEFT JOIN Viagem v ON m.cnh = v.motoristas_cnh
                    GROUP BY m.nome
                    ORDER BY total_viagens DESC
                    """;

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                String nomeMotorista = rs.getString("nome_motorista");
                Integer totalViagens = rs.getInt("total_viagens");

                result.add(new DriverTripCountDto(nomeMotorista, totalViagens));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar viagens por motorista: " + e.getMessage(), e);
        }
    }

    public List<DriverWithDestinationsDto> getDriversWithDestinations() {
        Map<String, DriverWithDestinationsDto> driverMap = new LinkedHashMap<>();
        String SQL = """
                    SELECT m.nome, v.destino, COUNT(v.id_viagem) AS total_viagens 
                    FROM Motoristas m
                    JOIN Viagem v ON m.cnh = v.motoristas_cnh
                    GROUP BY m.nome, v.destino
                    ORDER BY m.nome, total_viagens DESC
                    """;

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                String nomeMotorista = rs.getString("nome");
                String destino = rs.getString("destino");
                Integer totalViagens = rs.getInt("total_viagens");

                // Get or create the driver DTO
                DriverWithDestinationsDto driverDto = driverMap.computeIfAbsent(
                        nomeMotorista, name -> new DriverWithDestinationsDto(name, new ArrayList<>())
                );

                // Add the destination to this driver
                driverDto.addDestino(destino, totalViagens);
            }

            // Convert the map values to a list
            return new ArrayList<>(driverMap.values());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motoristas com destinos: " + e.getMessage(), e);
        }
    }


    // Não faz sentido ter
    @Override
    public void delete(String s) {

    }
}
