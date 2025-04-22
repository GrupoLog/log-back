package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.PhoneModel;
import com.cesar.bd_project.service.PhoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/telefones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService){
        this.phoneService = phoneService;
    }

    @GetMapping
    public List<PhoneModel> listPhones() {
        try {
            return phoneService.listPhones();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar telefones: " + e.getMessage());
        }
    }
}
