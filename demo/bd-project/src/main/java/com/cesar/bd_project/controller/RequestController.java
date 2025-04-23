package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.RequestModel;
import com.cesar.bd_project.service.RequestService;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }


    @GetMapping
    public List<RequestModel> listRequests() {
        try {
            return requestService.listRequests();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtoss: " + e.getMessage());
        }
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