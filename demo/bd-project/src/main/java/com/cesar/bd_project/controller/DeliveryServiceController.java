package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.DeliveryServiceModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.DeliveryServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos-entrega")
public class DeliveryServiceController {
    private final DeliveryServiceService deliveryServiceService;

    public DeliveryServiceController(DeliveryServiceService deliveryServiceService) {
        this.deliveryServiceService = deliveryServiceService;
    }

    @GetMapping
    public ResponseEntity<?> listDeliveryService() {
        try {
            List<DeliveryServiceModel> deliveryServiceList = deliveryServiceService.listDeliveryService();
            return ResponseEntity.ok(deliveryServiceList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar serviços de entrega: " + e.getMessage());
        }
    }

    @GetMapping("/{id-servico}")
    public ResponseEntity<?> findById(@PathVariable ("id-servico") Integer id) {
        try {
            DeliveryServiceModel Service = deliveryServiceService.findById(id);
            if (Service != null) {
                return ResponseEntity.ok(Service);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço de entrega não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar serviço de entrega: " + e.getMessage());
        }
    }

     @PostMapping
    public ResponseEntity<?> insertDeliveryService(@Valid @RequestBody DeliveryServiceModel deliveryService) {
        try {
            deliveryServiceService.insertDeliveryService(deliveryService);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Serviço de entrega inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir serviço de entrega: " + e.getMessage()));
        }
    }
}
