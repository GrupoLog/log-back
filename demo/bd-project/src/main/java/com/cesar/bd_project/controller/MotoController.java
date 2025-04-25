package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.MotoModel;
import com.cesar.bd_project.service.MotoService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService){
        this.motoService = motoService;
    }

    @GetMapping
    public List<MotoModel> listMotos() {
        try {
            return motoService.listMotos();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motos: " + e.getMessage());
        }
    }

    @GetMapping("/{chassi}")
    public MotoModel findById(@PathVariable String chassi) {
        try {
            return motoService.findById(chassi);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar moto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{chassi}")
    public String deleteMoto(@PathVariable String chassi) {
        try {
            motoService.deleteMoto(chassi);
            return "Moto deletada com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        }
    }
}
