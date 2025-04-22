package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ProductDao;
import com.cesar.bd_project.model.ProductModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public List<ProductModel> listProducts() throws SQLException {
        return productDao.list();
    }

    public ProductModel insertProduct(ProductModel product) throws SQLException {
        // Verifica se o id_produto não é nulo
        if(product.getIdProduto() == null){
            throw new IllegalArgumentException("id_produto é obrigatório");
        }

        // Verica se já existe no banco de dados
        if (productDao.findById(product.getIdProduto()) != null) {
            throw new IllegalArgumentException("Produto já cadastrado!");
        }

        return productDao.save(product);
    }

    public ProductModel findById(Integer id) throws SQLException{
        return productDao.findById(id);
    }

    public void updateProduct(ProductModel product) throws SQLException {
        if(product.getIdProduto() == null){
            throw new IllegalArgumentException("O campo id_produto é obrigatório!");
        }

        ProductModel existingProduct = productDao.findById(product.getIdProduto());
        if(existingProduct == null){
            System.err.println("Produto não encontrado!");
        }

        productDao.update(product);
    }

    public void deleteProduct(Integer id) throws SQLException{
        if (productDao.findById(id) == null) {
            throw new IllegalArgumentException("Produto não encontrado!");
        }
        productDao.delete(id);
    }
}
