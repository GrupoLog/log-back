package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.DeliveryServiceModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryServiceDao implements GenericDao<DeliveryServiceModel, Integer> {

    @Override
    public List<DeliveryServiceModel> list() {
        
        List<DeliveryServiceModel> deliveryServiceList = new ArrayList<>();
        String SQL = """
                SELECT s.id_servico, s.id_viagem, se.destinatario, se.peso_total, se.descricao_produto
                FROM servicos s
                JOIN servico_entrega se ON s.id_servico  = se.id_servico;
                """;
        try (Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

        while (rs.next()) {
            DeliveryServiceModel deliveryService = new DeliveryServiceModel();
            deliveryService.setIdServico(rs.getInt("id_servico"));
            deliveryService.setIdViagem(rs.getInt("id_viagem"));
            deliveryService.setDestinatario(rs.getString("destinatario"));
            deliveryService.setPesoTotal(rs.getInt("peso_total"));
            deliveryService.setDescricaoProduto(rs.getString("descricao_produto"));

            deliveryServiceList.add(deliveryService);
        }

        System.out.println("Servicos de entrega listados com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar servicos de entrega: " + e.getMessage(), e);
        }
        return deliveryServiceList;
    }

    @Override
    public DeliveryServiceModel findById(Integer id) {
        
        String SQL = "SELECT * FROM servicos WHERE id_servico = ?";
        DeliveryServiceModel service = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                service = new DeliveryServiceModel();
                service.setIdServico(rs.getInt("id_servico"));
                service.setIdViagem(rs.getInt("id_viagem"));
                System.out.println("Serviço encontrado!");
            } else {
                System.out.println("Serviço não encontrado!");
            }
            
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar serviço por ID: " + e.getMessage(), e);
        }
        return service;
    }

    @Override
    public void save(DeliveryServiceModel t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    // Não faz sentido ter
    @Override
    public void update(DeliveryServiceModel t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    // Não faz sentido ter
    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
