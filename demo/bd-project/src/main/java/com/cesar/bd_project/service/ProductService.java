package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ProductDao;
import com.cesar.bd_project.model.ProductModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public List<ProductModel> listProducts() {
        try {
            List<ProductModel> productList =productDao.list();
            if (productList.isEmpty()) {
                throw new IllegalStateException("Nenhum cliente encontrado.");
            }
            return productList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
    }

    public ProductModel findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido!");
        }
        return productDao.findById(id);
    }

    public void insertProduct(ProductModel product) {
        productDao.save(product);
    }

    public void updateProduct(ProductModel product) {
        if (product.getIdProduto() == null || product.getIdProduto() <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para atualização.");
        }
        ProductModel existingProduct = productDao.findById(product.getIdProduto());
        if(existingProduct == null){
            throw new IllegalArgumentException("Produto não encontrado para atualização.");
        }
        productDao.update(product);
    }

    // Não faz sentido ter delete
    public void deleteProduct(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido!");
        }
        ProductModel existingClient = productDao.findById(id);
        if (existingClient == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        productDao.delete(id);
    }
}
