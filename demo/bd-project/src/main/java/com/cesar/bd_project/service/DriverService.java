package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.DriverDao;
import com.cesar.bd_project.dto.DriverTripCountDto;
import com.cesar.bd_project.dto.DriverWithDestinationsDto;
import com.cesar.bd_project.dto.TotalDriverByTypeDto;
import com.cesar.bd_project.model.DriverModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    private final DriverDao driverDao;

    public DriverService(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    public List<DriverModel> listDrivers() {
        try {
            List<DriverModel> driverList = driverDao.list();
            if (driverList.isEmpty()) {
                throw new IllegalStateException("Nenhum morotista encontrado.");
            }

            return driverList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar morotistas: " + e.getMessage(), e);
        }
    }

    public DriverModel findById(String cnh) {
        if (cnh == null || cnh.isEmpty()) {
            throw new IllegalArgumentException("CNH não pode ser nulo ou vazio");
        }
        return driverDao.findById(cnh);
    }

    public DriverModel findByCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        return driverDao.findByCpf(cpf);
    }

    public void insertDriver(DriverModel driver) {
        DriverModel existingDriverByCnh = driverDao.findById(driver.getCnh());
        if(existingDriverByCnh != null) {
            throw new IllegalArgumentException("Motorista já cadastrado com essa CNH!");
        }
        DriverModel existingDriverByCpf = driverDao.findByCpf(driver.getCpf());
        if(existingDriverByCpf != null) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com esse CPF!");
        }
        if(driver.getTelefoneDois() == null || driver.getTelefoneDois().isEmpty()){
            driver.setTelefoneDois(null);
        }
        if(driver.getCnhSupervisionado() == null || driver.getCnhSupervisionado().isEmpty()){
            driver.setCnhSupervisionado(null);
        }
        driverDao.save(driver);
    }

    public void updateDriver(DriverModel driver) {
        // Só verifica se o motorista existe: não é possível atualizar CNH e CPF
        DriverModel existingDriverByCnh = driverDao.findById(driver.getCnh());
        if(existingDriverByCnh == null) {
            throw new IllegalArgumentException("Motorista não encontrado!");
        }
        if(driver.getTelefoneDois() == null || driver.getTelefoneDois().isEmpty()){
            driver.setTelefoneDois(null);
        }
        if(driver.getCnhSupervisionado() == null || driver.getCnhSupervisionado().isEmpty()){
            driver.setCnhSupervisionado(null);
        }
        driverDao.update(driver);
    }

    public List<TotalDriverByTypeDto> countDriversByType() {
        try {
            List<TotalDriverByTypeDto> result = driverDao.countDriversByType();
            if (result.isEmpty()) {
                List<TotalDriverByTypeDto> defaultResult = new ArrayList<>();
                defaultResult.add(new TotalDriverByTypeDto("Fixo", 0));
                defaultResult.add(new TotalDriverByTypeDto("Terceirizado", 0));
                return defaultResult;
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar motoristas por tipo: " + e.getMessage(), e);
        }
    }

    public List<DriverTripCountDto> countTripsByDriver() {
        try {
            return driverDao.countTripsByDriver();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar viagens por motorista: " + e.getMessage(), e);
        }
    }

    public List<DriverWithDestinationsDto> getDriversWithDestinations() {
        try {
            return driverDao.getDriversWithDestinations();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar motoristas com destinos: " + e.getMessage(), e);
        }
    }



}
