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
                JOIN servico_entrega se ON s.id_servico  = se.id_servico
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

        String SQL = """
                SELECT s.id_servico, s.id_viagem, se.destinatario, se.peso_total, se.descricao_produto
                FROM servicos s
                JOIN servico_entrega se ON s.id_servico  = se.id_servico
                WHERE s.id_servico = ?
                """;
        DeliveryServiceModel deliveryService = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                deliveryService = new DeliveryServiceModel();
                deliveryService.setIdServico(rs.getInt("id_servico"));
                deliveryService.setIdViagem(rs.getInt("id_viagem"));
                deliveryService.setDestinatario(rs.getString("destinatario"));
                deliveryService.setPesoTotal(rs.getInt("peso_total"));
                deliveryService.setDescricaoProduto(rs.getString("descricao_produto"));
                System.out.println("Serviço de entrega encontrado!");
            } else {
                System.out.println("Serviço de entrega não encontrado!");
            }
            
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar serviço de entrega por ID: " + e.getMessage(), e);
        }
        return deliveryService;
    }

    @Override
    public void save(DeliveryServiceModel deliveryService) {

        String SQL = "INSERT INTO servico_entrega(id_servico, destinatario, peso_total, descricao_produto) VALUES (?, ?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, deliveryService.getIdServico());
            stmt.setString(2, deliveryService.getDestinatario());
            stmt.setInt(3, deliveryService.getPesoTotal());
            stmt.setString(4, deliveryService.getDescricaoProduto());
            stmt.executeUpdate();
            System.out.println("Serviço de entrega inserido com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço de entrega no banco de dados: " + e.getMessage(), e);
        }
    }

    // Só pode atualizar destinatario e descricao do produto
    @Override
    public void update(DeliveryServiceModel deliveryService) {

        String SQL = "UPDATE servico_entrega SET destinatario = ?, descricao_produto = ? WHERE id_servico = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, deliveryService.getDestinatario());
            stmt.setString(2, deliveryService.getDescricaoProduto());
            stmt.setInt(3, deliveryService.getIdServico());
            stmt.executeUpdate();
            System.out.println("Servico de entrega atualizado com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar servico de entrega: " + e.getMessage(), e);
        }
    }

    // Não faz sentido ter
    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
