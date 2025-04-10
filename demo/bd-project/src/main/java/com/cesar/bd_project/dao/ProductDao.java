package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.utils.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProductDao implements GenericDao<ProductModel>{

    @Override
    public List<ProductModel> list() throws SQLException {

        List<ProductModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setId_produto(rs.getInt("id_produto"));
                product.setPeso(rs.getInt("peso"));
                product.setData_validade(rs.getDate("data_validade"));
                product.setDescricao(rs.getString("descricao"));
                lista.add(product);
            }
        }

        return lista;
    }

    @Override
    public void create(ProductModel productModel) {

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