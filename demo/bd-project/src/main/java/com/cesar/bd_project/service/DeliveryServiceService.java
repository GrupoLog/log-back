package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.DeliveryServiceDao;
import com.cesar.bd_project.dao.ServiceDao;
import com.cesar.bd_project.dao.TripDao;
import com.cesar.bd_project.model.DeliveryServiceModel;
import com.cesar.bd_project.model.TripModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceService {

    private final DeliveryServiceDao deliveryServiceDao;
    private final ServiceDao serviceDao;
    private final TripDao tripDao;


    public DeliveryServiceService(DeliveryServiceDao deliveryServiceDao, ServiceDao serviceDao, TripDao tripDao) {
        this.deliveryServiceDao = deliveryServiceDao;
        this.serviceDao = serviceDao;
        this.tripDao = tripDao;
    }

    public List<DeliveryServiceModel> listDeliveryService() {
        try {
            List<DeliveryServiceModel> deliveryServiceList = deliveryServiceDao.list();
            if (deliveryServiceList.isEmpty()) {
                throw new IllegalStateException("Serviço de entrega encontrado.");
            }

            return deliveryServiceList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar serviços de entrega: " + e.getMessage(), e);
        }
    }

    public DeliveryServiceModel findById(Integer id) {
        return deliveryServiceDao.findById(id);
    }

    public void insertDeliveryService(DeliveryServiceModel deliveryService) {
        TripModel existingTrip = tripDao.findById(deliveryService.getIdViagem());
        if(existingTrip == null) {
            throw new IllegalArgumentException("Viagem não encontrada.");
        }
        deliveryService.setIdServico(serviceDao.saveAndGetKey(deliveryService));
        deliveryServiceDao.save(deliveryService);
    }

    public void updateDeliveryService(DeliveryServiceModel deliveryService) {
        deliveryServiceDao.update(deliveryService);
    }
}
