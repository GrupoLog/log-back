package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.DriverModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
@Validated
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<?> listDrivers() {
        try {
            List<DriverModel> driverList = driverService.listDrivers();
            return ResponseEntity.ok(driverList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar motoristas: " + e.getMessage());
        }
    }

    @GetMapping("/{cnh}")
    public ResponseEntity<DriverModel> findById(@PathVariable String cnh) {
        return ResponseEntity.ok(driverService.findById(cnh));
    }

    @PostMapping
    public ResponseEntity<?> insertDriver(@Valid @RequestBody DriverModel driver) {
        try {
            driverService.insertDriver(driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Motorista inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir motorista: " + e.getMessage()));
        }
    }

}