package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.model.VehicleModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleDao vehicleDao;

    public VehicleService(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    public List<VehicleModel> listVehicles() throws SQLException {
        return vehicleDao.list();
    }

    public VehicleModel findById(String chassi) throws SQLException {
        return null;
    }


    public void insertVehicle(String chassi) throws SQLException{
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }

    }

    public void deleteVehicle(String chassi) throws SQLException {
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        if(vehicleDao.findById(chassi) == null){
            throw new IllegalArgumentException("Veiculo não encontrado!");
        }
        vehicleDao.delete(chassi);
    }

}
