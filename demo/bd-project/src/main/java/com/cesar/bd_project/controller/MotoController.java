package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.model.MotoModel;
import com.cesar.bd_project.service.MotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
