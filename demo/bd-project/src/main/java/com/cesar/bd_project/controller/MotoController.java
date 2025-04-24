package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.ClientModel;
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

    @DeleteMapping("/{chassi}")
    public String deleteMoto(@PathVariable String chassi) {
        try {
            motoService.deleteMoto(chassi);
            return "Moto deletada com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao deletar moto no banco de dados: " + e.getMessage();
        }
    }
}
