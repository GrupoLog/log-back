package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.TransportServiceModel;
import com.cesar.bd_project.service.TransportServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("servicos-transporte")
public class TransportServiceController {
    private final TransportServiceService transportServiceService;

    public TransportServiceController(TransportServiceService transportServiceService) {
        this.transportServiceService = transportServiceService;
    }

    @GetMapping
    public ResponseEntity<?> listDeliveryService() {
        try {
            List<TransportServiceModel> transportServiceList = transportServiceService.listTransportService();
            return ResponseEntity.ok(transportServiceList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar serviços de transporte: " + e.getMessage());
        }
    }
}
