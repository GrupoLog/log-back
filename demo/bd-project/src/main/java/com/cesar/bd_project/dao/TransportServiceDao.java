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
                FROM servicos s
                JOIN servico_transporte st ON s.id_servico  = st.id_servico
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
                FROM servicos s
                JOIN servico_transporte st ON s.id_servico  = st.id_servico
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
    public void save(TransportServiceModel transportServiceModel) {

    }

    @Override
    public void update(TransportServiceModel transportServiceModel) {

    }

    @Override
    public void delete(Integer integer) {

    }
}
