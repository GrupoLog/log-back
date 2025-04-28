package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.TransportServiceDao;
import com.cesar.bd_project.model.TransportServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportServiceService {

    private final TransportServiceDao transportServiceDao;

    public TransportServiceService(TransportServiceDao transportServiceDao) {
        this.transportServiceDao = transportServiceDao;
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
}
