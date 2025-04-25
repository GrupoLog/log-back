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
    public List<ProductModel> list() {

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

            System.out.println("Produtos listados com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return productList;
    }

    @Override
    public ProductModel findById(Integer id) {

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
                System.out.println("Produto encontrado!");
            } else {
                System.out.println("Produto não encontrado!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID: " + e.getMessage(), e);
        }
        return product;
    }

    @Override
    public void save(ProductModel product) {

        String SQL = "INSERT INTO Produtos(id_produto, peso, data_validade, descricao) VALUES(?, ?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, product.getIdProduto());
            stmt.setInt(2, product.getPeso());
            stmt.setDate(3, java.sql.Date.valueOf(product.getDataValidade()));
            stmt.setString(4, product.getDescricao());
            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto no banco de dados: " + e.getMessage(), e);
        }
    }



    @Override
    public void update(ProductModel product) {

        String SQL = "UPDATE Produtos SET peso = ?, data_validade = ?, descricao = ? WHERE id_produto = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, product.getPeso());
            stmt.setDate(2, java.sql.Date.valueOf(product.getDataValidade()));
            stmt.setString(3, product.getDescricao());
            stmt.setInt(4, product.getIdProduto());
            stmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer id) {

        String SQL = "DELETE FROM Produtos WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto deletado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }
}