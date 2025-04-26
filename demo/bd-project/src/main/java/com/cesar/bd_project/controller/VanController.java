package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.VanModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.VanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vans")
public class VanController {

    private final VanService vanService;

    public VanController(VanService vanService){
        this.vanService = vanService;
    }

    @GetMapping
    public ResponseEntity<?> listVans() {
        try {
            List<VanModel> vanList = vanService.listVans();
            return ResponseEntity.ok(vanList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar vans: " + e.getMessage());
        }
    }

    @GetMapping("/{chassi}")
    public ResponseEntity<?> findById(@PathVariable String chassi) {
        try {
            VanModel van = vanService.findById(chassi);
            if (van != null) {
                return ResponseEntity.ok(van);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Van não encontrada!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar van: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertVan(@RequestBody VanModel van) {
        try {
            vanService.insertVan(van);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Van inserida com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir van: " + e.getMessage()));
        }
    }

    @PutMapping("/{chassi}")
    public ResponseEntity<MessageResponse> updateVan(@PathVariable String chassi, @RequestBody VanModel van) {
        van.setChassi(chassi);
        try {
            vanService.updateVan(van);
            return ResponseEntity.ok(new MessageResponse("Van atualizada com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar van: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{chassi}")
    public ResponseEntity<MessageResponse> deleteVan(@PathVariable String chassi) {
        try {
            vanService.deleteVan(chassi);
            return ResponseEntity.ok(new MessageResponse("Van deletada com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar van: " + e.getMessage()));
        }
    }
}
