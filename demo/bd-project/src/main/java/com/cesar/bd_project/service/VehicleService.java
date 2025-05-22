package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.dto.MostUsedVehicleDto;
import com.cesar.bd_project.dto.TerceirizadosPercentageDto;
import com.cesar.bd_project.dto.UnusedVehiclesCountDto;
import com.cesar.bd_project.dto.VehicleCountDto;
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
        if(vehicle.getChassi() == null || vehicle.getChassi().isEmpty()) {
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        if(vehicle.getPlaca() == null || vehicle.getPlaca().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }
        if(vehicleDao.findById(vehicle.getChassi()) != null) {
            throw new IllegalArgumentException("Veiculo ja cadastrado com este chassi.");
        }
        if(vehicleDao.findByPlate(vehicle.getPlaca()) != null) {
            throw new IllegalArgumentException("Placa ja cadastrada.");
        }
        vehicleDao.save(vehicle);
    }

    public void updateVehicle(VehicleModel vehicle) {
        if(vehicle.getChassi() == null || vehicle.getChassi().isEmpty()) {
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        if(vehicle.getPlaca() == null || vehicle.getPlaca().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }

        VehicleModel existingVehicle = vehicleDao.findById(vehicle.getChassi());
        if (existingVehicle == null) {
            throw new IllegalArgumentException("Veículo não encontrado para o chassi fornecido.");
        }

        VehicleModel vehicleWithSamePlate = vehicleDao.findByPlate(vehicle.getPlaca());
        if (vehicleWithSamePlate != null && !vehicleWithSamePlate.getChassi().equals(vehicle.getChassi())) {
            throw new IllegalArgumentException("Placa já está em uso por outro veículo.");
        }
        vehicleDao.update(vehicle);
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

    public List<MostUsedVehicleDto> listarVeiculosMaisUsados() {
        return vehicleDao.listarVeiculosMaisUsados();
    }

    public VehicleCountDto countVehicles() {
        try {
            return vehicleDao.countVehicles();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar veículos: " + e.getMessage(), e);
        }
    }

    public UnusedVehiclesCountDto countUnusedVehicles() {
        try {
            return vehicleDao.countUnusedVehicles();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar veículos não utilizados: " + e.getMessage(), e);
        }
    }

    public TerceirizadosPercentageDto getTerceirizadosPercentage() {
        try {
            return vehicleDao.getTerceirizadosPercentage();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular percentagem de veículos terceirizados: " + e.getMessage(), e);
        }
    }



}
