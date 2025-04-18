package com.cesar.bd_project.controller;

import com.cesar.bd_project.client.MessageResponse;
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

    @PutMapping("/{id-produto}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable("id-produto") Integer idProduto, @RequestBody ProductModel product) {
        try {
            productService.updateProduct(product);
            return ResponseEntity.ok(new MessageResponse("Produto atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            //return "Erro de validação: " + e.getMessage();
            return ResponseEntity.ok(new MessageResponse("Erro de validação!"));
        } catch (SQLException e) {
            //return "Erro ao atualizar produto no banco de dados: " + e.getMessage();
            return ResponseEntity.ok(new MessageResponse("Erro ao atualizar produto no banco de dados"));

        }
    }


//    @DeleteMapping("/{id-produto}")
//    public String deleteProduct(@PathVariable String cpf) {
//        try {
//            productService.deleteProduct(cpf);
//            return "Produto deletado com sucesso!";
//        } catch (IllegalArgumentException e) {
//            return "Erro de validação: " + e.getMessage();
//        } catch (SQLException e) {
//            return "Erro ao deletar produto no banco de dados: " + e.getMessage();
//        }
//    }

}
