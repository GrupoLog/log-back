package com.cesar.bd_project.controller;

import com.cesar.bd_project.dto.MostUsedVehicleDto;
import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<?> listVehicles() {
        try {
            List<VehicleModel> vehicleList = vehicleService.listVehicles();
            return ResponseEntity.ok(vehicleList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar veiculos: " + e.getMessage());
        }
    }

    @GetMapping("/veiculos_mais_utilizados")
        public ResponseEntity<List<MostUsedVehicleDto>> listarVeiculosMaisUsados() {
            List<MostUsedVehicleDto> resultado = vehicleService.listarVeiculosMaisUsados();
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<?> insertVehicle(@RequestBody VehicleModel vehicle) {
        try {
            vehicleService.insertVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Veiculo inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir veiculo: " + e.getMessage()));
        }
    }

    @PutMapping("/{chassi}")
    public ResponseEntity<MessageResponse> updateVehicle(@PathVariable String chassi, @RequestBody VehicleModel vehicle) {
        vehicle.setChassi(chassi);
        try {
            vehicleService.updateVehicle(vehicle);
            return ResponseEntity.ok(new MessageResponse("Veiculo atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar veiculo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{chassi}")
    public ResponseEntity<MessageResponse> deleteVehicle(@PathVariable String chassi){
        try {
            vehicleService.deleteVehicle(chassi);
            return ResponseEntity.ok(new MessageResponse("Veiculo deletado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar veiculo: " + e.getMessage()));
        }
    }

}
