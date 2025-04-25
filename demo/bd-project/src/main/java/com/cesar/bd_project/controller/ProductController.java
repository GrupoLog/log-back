package com.cesar.bd_project.controller;

import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> listProducts() {
        try {
            List<ProductModel> clientList = productService.listProducts();
            return ResponseEntity.ok(clientList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertProduct( @RequestBody ProductModel product) {
        try {
            productService.insertProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Cliente inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir cliente: " + e.getMessage()));
        }
    }

    @PutMapping("/{id-produto}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable ("id-produto") Integer idProduto, @RequestBody ProductModel product) {
        product.setIdProduto(idProduto);
        try {
            productService.updateProduct(product);
            return ResponseEntity.ok(new MessageResponse("Produto atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            //return "Erro de validação: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            //return "Erro ao atualizar produto no banco de dados: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar produto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id-produto}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable("id-produto") Integer idProduto) {
        try {
            productService.deleteProduct(idProduto);
            return ResponseEntity.ok(new MessageResponse("Produto deletado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar produto no banco de dados: " + e.getMessage()));
        }
    }

}
