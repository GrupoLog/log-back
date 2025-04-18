package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ProductDao;
import com.cesar.bd_project.model.ProductModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    private final ProductDao dao;

    public ProductService(ProductDao dao){
        this.dao = dao;
    }

    public List<ProductModel> listProducts() throws SQLException {
        return dao.list();
    }

    public ProductModel insertProduct(ProductModel product) throws SQLException {
        // Validação
        if (product.getIdProduto() == null) {
            throw new IllegalArgumentException("Id_produto é obrigatório");
        }

        return dao.save(product);
    }

    public void updateProduct(ProductModel productModel) throws SQLException {
        dao.update(productModel);
    }

//    public void deleteProduct(int id) {
//        dao.delete(id);
//    }
}
