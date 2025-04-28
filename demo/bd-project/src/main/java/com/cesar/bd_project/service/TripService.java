package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.TripDao;
import com.cesar.bd_project.model.TripModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripDao tripDao;

    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
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

    public TripModel findById(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo ou vazio");
        }
        return tripDao.findById(id);
    }

    public void insertTrip(TripModel trip) {
        tripDao.save(trip);
    }
}
