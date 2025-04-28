package com.cesar.bd_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cesar.bd_project.dao.ServiceDao;
import com.cesar.bd_project.dao.TripDao;
import com.cesar.bd_project.model.ServiceModel;
import com.cesar.bd_project.model.TripModel;

@Service
public class ServiceService {
    
    private final ServiceDao serviceDao;
    private final TripDao tripDao;


    public ServiceService(ServiceDao serviceDao, TripDao tripDao) {
        this.serviceDao = serviceDao;
        this.tripDao = tripDao;
    }

    public List<ServiceModel> listService() {
        try {
            List<ServiceModel> serviceList = serviceDao.list();
            if (serviceList.isEmpty()) {
                throw new IllegalStateException("Nenhum serviço encontrado.");
            }

            return serviceList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar serviços: " + e.getMessage(), e);
        }
    }

    public ServiceModel findById(Integer id) {
        return serviceDao.findById(id);
    }

    public void insertService(ServiceModel service) {
        TripModel existingTrip = tripDao.findById(service.getIdViagem());
        if(existingTrip == null) {
            throw new IllegalArgumentException("Viagem não encontrada.");
        }
        serviceDao.save(service);
    }
}
