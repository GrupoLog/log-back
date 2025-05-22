package com.cesar.bd_project.controller;

import com.cesar.bd_project.dto.MonthlyRequestDto;
import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.dto.RevenueByPaymentKind;
import com.cesar.bd_project.dto.TopClientsByRequestsDto;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.RequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/solicitacoes_por_mes")
    public ResponseEntity<List<MonthlyRequestDto>> getSolicitacoesPorMes(@RequestParam(required = false) Integer ano) {
        if (ano == null) {
            ano = java.time.Year.now().getValue();
        }
        
        List<MonthlyRequestDto> resultado = requestService.contarSolicitacoesPorMes(ano);
        
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdWithDetails(@PathVariable ("id") Integer idSolicitacao) {
        try {
            Object request = requestService.findByIdWithDetail(idSolicitacao);
            if (request != null) {
                return ResponseEntity.ok(request);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitação não encontrada!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar solicitação: " + e.getMessage());
        }
    }

    // Removed duplicate method

    @GetMapping("/top_clientes")
    public ResponseEntity<List<TopClientsByRequestsDto>> getClientesComMaisSolicitacoes() {
        List<TopClientsByRequestsDto> resultado = requestService.buscarClientesComMaisSolicitacoes();
        return ResponseEntity.ok(resultado);
    }

    //  @PostMapping
    //  public ResponseEntity<?> insertRequest(@RequestBody RequestDto request) {
    //      try {
    //          requestService.insertRequest(request);
    //          return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Solicitacao inserida com sucesso!"));
    //      } catch (IllegalArgumentException e) {
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
    //      } catch (Exception e) {
    //          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir solicitacao: " + e.getMessage()));
    //      }
    //  }



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