package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.DriverModel;
import com.cesar.bd_project.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
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
}
