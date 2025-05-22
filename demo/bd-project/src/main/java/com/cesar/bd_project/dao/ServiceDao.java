package com.cesar.bd_project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cesar.bd_project.dto.MonthlyServiceCountDto;
import com.cesar.bd_project.dto.RevenueByServiceKind;
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

    public List<MonthlyServiceCountDto> contarServicosPorMes(int ano) {
    String sql = """
        WITH servicos_tipo AS (
            SELECT s.id_servico, 'Entrega' AS tipo_servico
            FROM Servicos s
            JOIN Servico_entrega se ON s.id_servico = se.id_servico

            UNION ALL

            SELECT s.id_servico, 'Transporte' AS tipo_servico
            FROM Servicos s
            JOIN Servico_transporte st ON s.id_servico = st.id_servico
        )

        SELECT 
            MONTH(v.data_viagem) AS mes,
            st.tipo_servico,
            COUNT(*) AS total
        FROM viagem v
        JOIN Servicos s ON v.id_viagem = s.id_viagem
        JOIN servicos_tipo st ON s.id_servico = st.id_servico
        WHERE YEAR(v.data_viagem) = ?
        GROUP BY MONTH(v.data_viagem), st.tipo_servico
        ORDER BY mes;
    """;

    List<MonthlyServiceCountDto> resultado = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, ano);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MonthlyServiceCountDto dto = new MonthlyServiceCountDto();
            dto.setMes(rs.getInt("mes"));
            dto.setTipoServico(rs.getString("tipo_servico"));
            dto.setQuantidade(rs.getInt("total"));
            resultado.add(dto);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao contar serviços por mês: " + e.getMessage(), e);
    }

        return resultado;
    }

    public List<RevenueByServiceKind> calcularReceitaPorTipo() {
        String sql = """
            WITH servicos_tipo AS (
                SELECT se.id_servico, 'entrega' AS tipo
                FROM Servico_entrega se

                UNION ALL

                SELECT st.id_servico, 'transporte' AS tipo
                FROM Servico_transporte st
            )

            SELECT st.tipo, SUM(soli.valor_pagamento) AS receita_total
            FROM servicos_tipo st
            JOIN Servicos s ON st.id_servico = s.id_servico
            JOIN Solicitacoes soli ON soli.id_servico = s.id_servico
            GROUP BY st.tipo;
        """;
    
        List<RevenueByServiceKind> resultado = new ArrayList<>();
    
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                resultado.add(new RevenueByServiceKind(
                    rs.getString("tipo"),
                    rs.getDouble("receita_total")
                ));
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular receita por tipo: " + e.getMessage(), e);
        }
    
        return resultado;
    }
    


    // Não faz sentido ter
    @Override
    public void update(ServiceModel t) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    // Não faz sentido ter
    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}

