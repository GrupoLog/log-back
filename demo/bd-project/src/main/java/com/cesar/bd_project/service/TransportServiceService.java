package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ServiceDao;
import com.cesar.bd_project.dao.TransportServiceDao;
import com.cesar.bd_project.dao.TripDao;
import com.cesar.bd_project.model.TransportServiceModel;
import com.cesar.bd_project.model.TripModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportServiceService {

    private final TransportServiceDao transportServiceDao;
    private final ServiceDao serviceDao;
    private final TripDao tripDao;

    public TransportServiceService(TransportServiceDao transportServiceDao, ServiceDao serviceDao, TripDao tripDao) {
        this.transportServiceDao = transportServiceDao;
        this.serviceDao = serviceDao;
        this.tripDao = tripDao;
    }

    public List<TransportServiceModel> listTransportService() {
        try {
            List<TransportServiceModel> transportServiceList = transportServiceDao.list();
            if (transportServiceList.isEmpty()) {
                throw new IllegalStateException("Serviço de transporte encontrado.");
            }

            return transportServiceList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar serviços de transporte: " + e.getMessage(), e);
        }
    }

    public TransportServiceModel findById(Integer id) {
        return transportServiceDao.findById(id);
    }

    public void insertTransportService(TransportServiceModel transportService) {
        TripModel existingTrip = tripDao.findById(transportService.getIdViagem());
        if(existingTrip == null) {
            throw new IllegalArgumentException("Viagem não encontrada.");
        }
        transportService.setIdServico(serviceDao.saveAndGetKey(transportService));
        transportServiceDao.save(transportService);
    }

    public void updateTransportService(TransportServiceModel transportService) {
        transportServiceDao.update(transportService);
    }
}

