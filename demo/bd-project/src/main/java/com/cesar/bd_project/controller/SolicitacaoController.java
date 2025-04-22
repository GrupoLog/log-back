package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.SolicitacaoModel;
import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.service.SolicitacaoService;
import com.cesar.bd_project.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService){
        this.solicitacaoService = solicitacaoService;
    }


    @GetMapping
    public List<SolicitacaoModel> listarSolicitacoes() {
        return solicitacaoService.listarSolicitacoes();
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