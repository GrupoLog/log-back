package com.cesar.bd_project.controller;

import com.cesar.bd_project.client.MessageResponse;
import com.cesar.bd_project.model.PhoneModel;
import com.cesar.bd_project.service.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/telefones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService){
        this.phoneService = phoneService;
    }

    @GetMapping
    public List<PhoneModel> listPhones() {
        try {
            return phoneService.listPhones();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar telefones: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertPhone(@RequestBody PhoneModel phone) {
        try {
            PhoneModel savedPhone = phoneService.insertPhone(phone);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPhone);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar telefone no banco de dados: " + e.getMessage());
        }
    }

}
