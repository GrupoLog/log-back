package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.DriverDao;
import com.cesar.bd_project.dao.TripDao;
import com.cesar.bd_project.dao.VehicleDao;
import com.cesar.bd_project.dto.TripDto;
import com.cesar.bd_project.dto.TripWithDetailDto;
import com.cesar.bd_project.model.DriverModel;
import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.model.VehicleModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripDao tripDao;
    private final VehicleDao vehicleDao;
    private final DriverDao driverDao;

    public TripService(TripDao tripDao, VehicleDao vehicleDao, DriverDao driverDao) {
        this.tripDao = tripDao;
        this.vehicleDao = vehicleDao;
        this.driverDao = driverDao;
    }

    public List<TripModel> listTrips() {
        try {
            List<TripModel> tripList = tripDao.list();
            if (tripList.isEmpty()) {
                throw new IllegalStateException("Nenhuma viagem encontrada.");
            }

            return tripList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
    }

    public List<TripDto> listTripWithDetails() {
        try {
            List<TripDto> tripList = tripDao.listWithDetail();
            if (tripList.isEmpty()) {
                throw new IllegalStateException("Nenhuma viagem encontrada.");
            }

            return tripList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
    }

    public TripModel findById(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo ou vazio");
        }
        return tripDao.findById(id);
    }

    public TripWithDetailDto findByIdWithDetails(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo ou vazio");
        }
        return tripDao.findByIdWithDetail(id);
    }

    public void insertTrip(TripModel trip) {
        VehicleModel existingVehicle = vehicleDao.findById(trip.getVeiculoChassi());
        if(existingVehicle == null) {
            throw new IllegalArgumentException("Veículo não encontrado para o chassi fornecido.");
        }

        DriverModel existingDriver = driverDao.findById(trip.getMotoristasCnh());
        if(existingDriver == null) {
            throw new IllegalArgumentException("Motorista não encontrado com essa CNH!");
        }
        tripDao.save(trip);
    }

    public void updateTrip(TripModel trip) {
        VehicleModel existingVehicle = vehicleDao.findById(trip.getVeiculoChassi());
        if(existingVehicle == null) {
            throw new IllegalArgumentException("Veículo não encontrado para o chassi fornecido.");
        }

        DriverModel existingDriver = driverDao.findById(trip.getMotoristasCnh());
        if(existingDriver == null) {
            throw new IllegalArgumentException("Motorista não encontrado com essa CNH!");
        }
        tripDao.update(trip);
    }
}
