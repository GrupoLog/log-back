package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.MotoModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.MotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService){
        this.motoService = motoService;
    }

    @GetMapping
    public ResponseEntity<?> listMotos() {
        try {
            List<MotoModel> motoList = motoService.listMotos();
            return ResponseEntity.ok(motoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar motos: " + e.getMessage());
        }
    }

    @GetMapping("/{chassi}")
    public ResponseEntity<?> findById(@PathVariable String chassi) {
        try {
            MotoModel moto = motoService.findById(chassi);
            if (moto != null) {
                return ResponseEntity.ok(moto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Moto não encontrada!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar moto: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertMoto(@RequestBody MotoModel moto) {
        try {
            motoService.insertMoto(moto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Moto inserida com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir moto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{chassi}")
    public ResponseEntity<MessageResponse> deleteMoto(@PathVariable String chassi) {
        try {
            motoService.deleteMoto(chassi);
            return ResponseEntity.ok(new MessageResponse("Moto deletada com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar moto: " + e.getMessage()));
        }
    }
}
