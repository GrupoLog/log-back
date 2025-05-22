package com.cesar.bd_project.controller; 

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cesar.bd_project.dto.MonthlyServiceCountDto;
import com.cesar.bd_project.dto.PaymentDistributionDto;
import com.cesar.bd_project.dto.RevenueByServiceKind;
import com.cesar.bd_project.model.ServiceModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.ServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/servicos")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<?> listService() {
        try {
            List<ServiceModel> serviceList = serviceService.listService();
            return ResponseEntity.ok(serviceList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar serviços: " + e.getMessage());
        }
    }

    @GetMapping("/{id-servico}")
    public ResponseEntity<?> findById(@PathVariable ("id-servico") Integer id) {
        try {
            ServiceModel Service = serviceService.findById(id);
            if (Service != null) {
                return ResponseEntity.ok(Service);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar Serviço: " + e.getMessage());
        }
    }

    @GetMapping("/contar_servicos_mensais")
        public ResponseEntity<List<MonthlyServiceCountDto>> contarServicosMensais(@RequestParam(required = false) Integer ano) {
            if (ano == null) {
                ano = java.time.Year.now().getValue();
            }

            List<MonthlyServiceCountDto> resultado = serviceService.contarServicosPorMes(ano);
            return ResponseEntity.ok(resultado);
        }

    @GetMapping("/receita_por_tipo")
    public ResponseEntity<List<RevenueByServiceKind>> getReceitaPorTipo() {
        List<RevenueByServiceKind> resultado = serviceService.calcularReceitaPorTipo();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/distribuicao_pagamento")
    public ResponseEntity<List<PaymentDistributionDto>> getDistribuicaoPorFormaPagamento() {
        List<PaymentDistributionDto> resultado = serviceService.calcularDistribuicaoPorFormaPagamento();
        return ResponseEntity.ok(resultado);
    }


     @PostMapping
    public ResponseEntity<?> insertService(@Valid @RequestBody ServiceModel service) {
        try {
            serviceService.insertService(service);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Serviço inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir Serviço: " + e.getMessage()));
        }
    }
}
