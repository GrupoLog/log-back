package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao implements GenericDao<ProductModel, Integer>{

    @Override
    public List<ProductModel> list() throws SQLException {

        List<ProductModel> productList = new ArrayList<>();
        String SQL = "SELECT * FROM Produtos";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setIdProduto(rs.getInt("id_produto"));
                product.setPeso(rs.getInt("peso"));
                product.setDataValidade(rs.getDate("data_validade").toLocalDate());
                product.setDescricao(rs.getString("descricao"));
                productList.add(product);
            }
        }
        return productList;
    }

    @Override
    public ProductModel save(ProductModel product) throws SQLException {
        String SQL = "INSERT INTO Produtos(id_produto, peso, data_validade, descricao) VALUES(?, ?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, product.getIdProduto());
            stmt.setInt(2, product.getPeso());
            stmt.setDate(3, java.sql.Date.valueOf(product.getDataValidade()));
            stmt.setString(4, product.getDescricao());
            stmt.executeUpdate();
        }
        return product;
    }

    @Override
    public ProductModel findById(Integer id) throws SQLException {
        String SQL = "SELECT * FROM Produtos WHERE id_produto = ?";
        ProductModel product = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                product = new ProductModel();
                product.setIdProduto(rs.getInt("id_produto"));
                product.setPeso(rs.getInt("peso"));
                product.setDataValidade(rs.getDate("data_validade").toLocalDate());
                product.setDescricao(rs.getString("descricao"));
            }

        }
        return product;
    }


    @Override
    public void update(ProductModel product) throws SQLException {
        String SQL = "UPDATE Produtos SET peso = ?, data_validade = ?, descricao = ? WHERE id_produto = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, product.getPeso());
            stmt.setDate(2, java.sql.Date.valueOf(product.getDataValidade()));
            stmt.setString(3, product.getDescricao());
            stmt.setInt(4, product.getIdProduto());
            stmt.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException{
        String SQL = "DELETE FROM Produtos WHERE id_produto = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}