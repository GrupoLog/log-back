package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.model.VehicleModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleDao vehicleDao;

    public VehicleService(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    public List<VehicleModel> listVehicles() {
        try {
            List<VehicleModel> vehicleList = vehicleDao.list();
            if (vehicleList.isEmpty()) {
                throw new IllegalStateException("Nenhum veiculo encontrado.");
            }

            return vehicleList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage(), e);
        }
    }

    public VehicleModel findById(String chassi) {
        if (chassi == null || chassi.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        return vehicleDao.findById(chassi);
    }

    public void insertVehicle(VehicleModel vehicle) {
        if(vehicle.getChassi() == null || vehicle.getChassi().isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        if(vehicleDao.findById(vehicle.getChassi()) != null){
            throw new IllegalArgumentException("Veiculo ja cadastrado.");
        }
        vehicleDao.save(vehicle);
    }

    public void deleteVehicle(String chassi) {
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        if(vehicleDao.findById(chassi) == null){
            throw new IllegalArgumentException("Veiculo não encontrado!");
        }
        vehicleDao.delete(chassi);
    }

}
