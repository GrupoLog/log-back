package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.TransportServiceModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransportServiceDao implements GenericDao<TransportServiceModel, Integer> {
    @Override
    public List<TransportServiceModel> list() {

        List<TransportServiceModel> transportServiceList = new ArrayList<>();
        String SQL = """
                SELECT s.id_servico, s.id_viagem, st.qtd_passageiros, st.descricao_transporte
                FROM Servicos s
                JOIN Servico_Transporte st ON s.id_servico  = st.id_servico
                """;
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                TransportServiceModel transportService = new TransportServiceModel();
                transportService.setIdServico(rs.getInt("id_servico"));
                transportService.setIdViagem(rs.getInt("id_viagem"));
                transportService.setQtdPassageiros(rs.getInt("qtd_passageiros"));
                transportService.setDescricaoTransporte(rs.getString("descricao_transporte"));
                transportServiceList.add(transportService);
            }

            System.out.println("Servicos de transporte listados com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar servicos de transporte: " + e.getMessage(), e);
        }
        return transportServiceList;
    }

    @Override
    public TransportServiceModel findById(Integer id) {
        String SQL = """
                SELECT s.id_servico, s.id_viagem, st.qtd_passageiros, st.descricao_transporte
                FROM Servicos s
                JOIN Servico_Transporte st ON s.id_servico  = st.id_servico
                WHERE s.id_servico = ?
                """;
        TransportServiceModel transportService = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                transportService = new TransportServiceModel();
                transportService.setIdServico(rs.getInt("id_servico"));
                transportService.setIdViagem(rs.getInt("id_viagem"));
                transportService.setQtdPassageiros(rs.getInt("qtd_passageiros"));
                transportService.setDescricaoTransporte(rs.getString("descricao_transporte"));
                System.out.println("Serviço de transporte encontrado!");
            } else {
                System.out.println("Serviço de transporte não encontrado!");
            }

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar serviço de transporte por ID: " + e.getMessage(), e);
        }
        return transportService;
    }

    @Override
    public void save(TransportServiceModel transportService) {
        String SQL = "INSERT INTO Servico_Transporte(id_servico, qtd_passageiros, descricao_transporte) VALUES (?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, transportService.getIdServico());
            stmt.setInt(2, transportService.getQtdPassageiros());
            stmt.setString(3, transportService.getDescricaoTransporte());
            stmt.executeUpdate();
            System.out.println("Serviço de transporte inserido com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço de transporte no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(TransportServiceModel transportService) {

        String SQL = "UPDATE Servico_Transporte SET qtd_passageiros = ?, descricao_transporte = ? WHERE id_servico = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, transportService.getQtdPassageiros());
            stmt.setString(2, transportService.getDescricaoTransporte());
            stmt.setInt(3, transportService.getIdServico());
            stmt.executeUpdate();
            System.out.println("Servico de transporte atualizado com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar servico de transporte: " + e.getMessage(), e);
        }
    }

    // Nao faz sentido ter
    @Override
    public void delete(Integer integer) {

    }
}
