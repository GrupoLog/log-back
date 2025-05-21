package com.cesar.bd_project.controller;

import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.dto.RevenueByPaymentKind;
import com.cesar.bd_project.service.RequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<?> listRequests() {
        try {
            List<RequestDto> requestList = requestService.listRequests();
            return ResponseEntity.ok(requestList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar solicitacoes: " + e.getMessage());
        }
    }

    @GetMapping("/receita_por_forma_pagamento")
    public ResponseEntity<List<RevenueByPaymentKind>> getRevenueByPaymentKind() {
        List<RevenueByPaymentKind> revenueList = requestService.getRevenueByPaymentKind();
        return ResponseEntity.ok(revenueList);
    }

    @GetMapping("/revenue-total")
    public ResponseEntity<Double> getReceitaTotalPorAno(@RequestParam(required = false) Integer ano) {
        if (ano == null) {
            ano = java.time.Year.now().getValue();
        }

        Double receitaTotal = requestService.getReceitaTotalPorAno(ano);
        return ResponseEntity.ok(receitaTotal);
    }

    // @PostMapping
    // public ResponseEntity<SolicitacaoModel> criarSolicitacao(@RequestBody SolicitacaoModel solicitacao) {
    //     solicitacoes.add(solicitacao);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(solicitacao);
    // }


    // @GetMapping("/{id}")
    // public ResponseEntity<SolicitacaoModel> buscarSolicitacaoPorId(@PathVariable int id) {
    //     return solicitacoes.stream()
    //             .filter(solicitacao -> solicitacao.getIdSolicitacao() == id)
    //             .findFirst()
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<SolicitacaoModel> atualizarSolicitacao(@PathVariable int id, @RequestBody SolicitacaoModel solicitacaoAtualizada) {
    //     for (int i = 0; i < solicitacoes.size(); i++) {
    //         if (solicitacoes.get(i).getIdSolicitacao() == id) {
    //             solicitacoes.set(i, solicitacaoAtualizada);
    //             return ResponseEntity.ok(solicitacaoAtualizada);
    //         }
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletarSolicitacao(@PathVariable int id) {
    //     boolean removed = solicitacoes.removeIf(solicitacao -> solicitacao.getIdSolicitacao() == id);
    //     if (removed) {
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }
}