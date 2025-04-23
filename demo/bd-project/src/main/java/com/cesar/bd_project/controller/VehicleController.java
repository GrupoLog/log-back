package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.service.VehicleService;
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
