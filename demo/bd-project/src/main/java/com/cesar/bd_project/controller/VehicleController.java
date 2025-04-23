package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.ProductModel;
import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<VehicleModel> listVehicles() {
        try {
            return vehicleService.listVehicles();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtoss: " + e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> insertVehicle(@RequestBody VehicleModel vehicle) {
        try {
            VehicleModel savedVehicle = vehicleService.insertVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar veiculo no banco de dados: " + e.getMessage());
        }
    }

    @DeleteMapping("/{chassi}")
    public String deleteVehicle(@PathVariable String chassi){
        try {
            vehicleService.deleteVehicle(chassi);
            return "Veiculo deletado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao deletar veiculo no banco de dados: " + e.getMessage();
        }
    }
}
