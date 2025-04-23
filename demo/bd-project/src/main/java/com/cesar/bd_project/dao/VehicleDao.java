package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDao implements GenericDao<VehicleModel, String> {

    @Override
    public List<VehicleModel> list() throws SQLException {
        List<VehicleModel> vehicleList = new ArrayList<>();
        String SQL = "SELECT * FROM Veiculo";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                VehicleModel vehicle = new VehicleModel();
                vehicle.setChassi(rs.getString("chassi"));
                vehicle.setProprietario(rs.getString("proprietario"));
                vehicle.setPlaca(rs.getString("placa"));
                vehicleList.add(vehicle);
            }
        }
        return vehicleList;
    }

    @Override
    public VehicleModel save(VehicleModel vehicleModel) throws SQLException {
        return null;
    }

    @Override
    public VehicleModel findById(String chassi) throws SQLException {
        String SQL = "SELECT * FROM veiculo WHERE chassi = ?";
        VehicleModel vehicle = null;
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                vehicle = new VehicleModel();
                vehicle.setChassi(rs.getString("chassi"));
                vehicle.setProprietario(rs.getString("proprietario"));
                vehicle.setPlaca(rs.getString("placa"));
            }
        }
        return vehicle;
    }

    @Override
    public void update(VehicleModel vehicleModel) throws SQLException {

    }

    @Override
    public void delete(String chassi) throws SQLException {
        String SQL = "DELETE FROM veiculo WHERE chassi = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, chassi);
            stmt.executeUpdate();
        }

    }
}
