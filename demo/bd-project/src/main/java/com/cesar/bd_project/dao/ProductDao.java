package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao implements GenericDao<ProductModel>{

    @Override
    public List<ProductModel> list() throws SQLException {

        List<ProductModel> listProduct = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setIdProduto(rs.getInt("id_produto"));
                product.setPeso(rs.getInt("peso"));
                product.setDataValidade(rs.getDate("data_validade").toLocalDate());
                product.setDescricao(rs.getString("descricao"));
                listProduct.add(product);
            }
        }
        return listProduct;
    }

    @Override
    public ProductModel save(ProductModel productModel) throws SQLException {
        String sql = "INSERT INTO Produtos(id_produto, peso, data_validade, descricao) VALUES(?, ?, ?, ?)";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productModel.getIdProduto());
            stmt.setInt(2, productModel.getPeso());
            stmt.setDate(3, java.sql.Date.valueOf(productModel.getDataValidade()));
            stmt.setString(4, productModel.getDescricao());
            stmt.executeUpdate();
        }
        return productModel;
    }

    @Override
    public Optional<ProductModel> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(ProductModel productModel, int id) {

    }

    @Override
    public void delete(int id) {

    }
}