package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.DriverDao;
import com.cesar.bd_project.model.DriverModel;
import org.springframework.stereotype.Service;

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
}
