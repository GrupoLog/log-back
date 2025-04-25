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

    public List<ProductModel> listProducts() {
        return productDao.list();
    }

    public ProductModel findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException("id_produto não pode ser nulo ou vazio");
        }
        return productDao.findById(id);
    }

    public void insertProduct(ProductModel product) {
        // Verifica se o id_produto não é nulo
        if(product.getIdProduto() == null){
            throw new IllegalArgumentException("id_produto é obrigatório");
        }

        // Verica se já existe no banco de dados
        if (productDao.findById(product.getIdProduto()) != null) {
            throw new IllegalArgumentException("Produto já cadastrado!");
        }

        productDao.save(product);
    }

    public void updateProduct(ProductModel product) {
        if(product.getIdProduto() == null){
            throw new IllegalArgumentException("O campo id_produto é obrigatório!");
        }

        ProductModel existingProduct = productDao.findById(product.getIdProduto());
        if(existingProduct == null){
            System.err.println("Produto não encontrado!");
        }

        productDao.update(product);
    }

    public void deleteProduct(Integer id) {
        if (productDao.findById(id) == null) {
            throw new IllegalArgumentException("Produto não encontrado!");
        }

        ProductModel existingClient = productDao.findById(id);
        if (existingClient == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        productDao.delete(id);
    }
}
