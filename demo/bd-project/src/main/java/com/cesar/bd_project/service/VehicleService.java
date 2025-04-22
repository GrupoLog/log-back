package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.model.VehicleModel;
import com.cesar.bd_project.utils.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleDao vehicleDao;

    public VehicleService(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    public List<VehicleModel> listVehicles() throws SQLException {

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

}
