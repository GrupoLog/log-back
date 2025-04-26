package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.VanDao;
import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.model.VanModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VanService {

    private final VanDao vanDao;
    private final VehicleDao vehicleDao;

    public VanService(VanDao vanDao, VehicleDao vehicleDao){
        this.vanDao = vanDao;
        this.vehicleDao = vehicleDao;
    }

    public List<VanModel> listVans() {
        try {
            List<VanModel> vanList = vanDao.list();
            if (vanList.isEmpty()) {
                throw new IllegalStateException("Nenhuma van encontrada.");
            }

            return vanList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar vans: " + e.getMessage(), e);
        }
    }

    public VanModel findById(String chassi) {
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        return vanDao.findById(chassi);
    }

    public void insertVan(VanModel van) {
        // Validação
        if (van.getChassi() == null || van.getChassi().isEmpty()) {
            throw new IllegalArgumentException("Chassi é obrigatório.");
        }
        if(vehicleDao.findById(van.getChassi()) != null) {
            throw new IllegalArgumentException("Van já cadastrada.");
        }
        vehicleDao.save(van);
        vanDao.save(van);
    }

    public void updateVan(VanModel van) {

        if (van.getChassi() == null || van.getChassi().isEmpty()) {
            throw new IllegalArgumentException("Chassi é obrigatório.");
        }
        if(vehicleDao.findById(van.getChassi()) == null) {
            throw new IllegalArgumentException("Van não cadastrada.");
        }
        if (van.getCapacidadePassageiros() < 0) {
            throw new IllegalArgumentException("Capacidade de passageiros não pode ser negativa.");
        }
        vehicleDao.update(van);
        vanDao.update(van);
    }

    public void deleteVan(String chassi){
        // Verifica se o CPF é nulo ou vazio
        if (chassi == null || chassi.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        VanModel existingVan = vanDao.findById(chassi);
        if (existingVan == null) {
            throw new IllegalArgumentException("Van não encontrada.");
        }
        vanDao.delete(chassi);
    }
}
