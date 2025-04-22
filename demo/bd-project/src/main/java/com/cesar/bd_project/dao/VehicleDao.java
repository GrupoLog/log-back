package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.VehicleModel;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class VehicleDao implements GenericDao<VehicleModel, String> {

    @Override
    public List<VehicleModel> list() throws SQLException {
        return List.of();
    }

    @Override
    public VehicleModel save(VehicleModel vehicleModel) throws SQLException {
        return null;
    }

    @Override
    public VehicleModel findById(String s) throws SQLException {
        return null;
    }

    @Override
    public void update(VehicleModel vehicleModel) throws SQLException {

    }

    @Override
    public void delete(String s) throws SQLException {

    }
}
