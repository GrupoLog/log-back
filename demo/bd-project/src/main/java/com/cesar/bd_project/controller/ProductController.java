package com.cesar.bd_project.controller;

import com.cesar.bd_project.client.ClientModel;
import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<ProductModel> listProducts() {
        try {
            return productService.listProducts();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtoss: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertProduct(@RequestBody ProductModel product) {
        try {
            ProductModel savedProduct = productService.insertProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar produto no banco de dados: " + e.getMessage());
        }
    }

}
