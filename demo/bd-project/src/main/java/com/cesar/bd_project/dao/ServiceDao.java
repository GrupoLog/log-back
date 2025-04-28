package com.cesar.bd_project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cesar.bd_project.model.ServiceModel;
import com.cesar.bd_project.utils.ConnectionFactory;

@Repository
public class ServiceDao implements GenericDao<ServiceModel, Integer> {

    @Override
    public List<ServiceModel> list() {

        List<ServiceModel> serviceList = new ArrayList<>();
        String SQL = "SELECT * FROM servicos";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                ServiceModel service = new ServiceModel();
                service.setIdServico(rs.getInt("id_servico"));
                service.setIdViagem(rs.getInt("id_viagem"));
                serviceList.add(service);
            }

            System.out.println("Servicos listados com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar servicos: " + e.getMessage(), e);
        }
        return serviceList;
    }

    @Override
    public ServiceModel findById(Integer id) {

        String SQL = "SELECT * FROM servicos WHERE id_servico = ?";
        ServiceModel service = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                service = new ServiceModel();
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
    public void save(ServiceModel service) {

        String SQL = "INSERT INTO servicos(id_viagem) VALUES (?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, service.getIdViagem());
            stmt.executeUpdate();
            System.out.println("Serviço inserida com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço no banco de dados: " + e.getMessage(), e);
        }
    }

    // Metodo necessario para salvar servicos de transporte e entrega
    public int saveAndGetKey(ServiceModel service) {

        String SQL = "INSERT INTO Servicos (id_viagem) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, service.getIdViagem());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o id_servico gerado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço: " + e.getMessage(), e);
        }
    }

    // Não faz sentido ter
    @Override
    public void update(ServiceModel t) {
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

