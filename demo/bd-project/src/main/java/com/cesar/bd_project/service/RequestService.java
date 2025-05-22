package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.*;
import com.cesar.bd_project.dto.MonthlyRequestDto;
import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.dto.RevenueByPaymentKind;
import com.cesar.bd_project.dto.TopClientsByRequestsDto;
import com.cesar.bd_project.dto.TotalRequestsDto;
import com.cesar.bd_project.mapper.ClassMapper;
import com.cesar.bd_project.model.*;
import org.springframework.stereotype.Service;
import com.cesar.bd_project.dto.RequestWithTransportDetailDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestDao requestDao;
    private final TripDao tripDao;
    private final ServiceDao serviceDao;
    private final ClientDao clientDao;
    private final DeliveryServiceDao deliveryServiceDao;
    private final TransportServiceDao transportServiceDao;
    private final ProductDao productDao;


    public RequestService(RequestDao requestDao, TripDao tripDao, ServiceDao serviceDao, ClientDao clientDao, DeliveryServiceDao deliveryServiceDao, TransportServiceDao transportServiceDao, ProductDao productDao) {
        this.requestDao = requestDao;
        this.tripDao = tripDao;
        this.serviceDao = serviceDao;
        this.clientDao = clientDao;
        this.deliveryServiceDao = deliveryServiceDao;
        this.transportServiceDao = transportServiceDao;
        this.productDao = productDao;
    }


    public List<RequestDto> listRequests() {
        try {
            List<RequestModel> requestList = requestDao.list();
            List<RequestDto> requestListDto = new ArrayList<>();
            for (RequestModel request : requestList) {
                requestListDto.add(ClassMapper.toRequestDto(request));
            }
            if (requestListDto.isEmpty()) {
                throw new IllegalStateException("Nenhuma solicitacao encontrada.");
            }
            return requestListDto;
        }catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar solicitacoes: " + e.getMessage(), e);
        }
    }

    public Object findByIdWithDetail(int id) {
        RequestModel requestModel = requestDao.findById(id);
        Integer idServico = requestModel.getIdServico();
        System.out.println(idServico);
        Integer idTrip = serviceDao.findById(idServico).getIdViagem();
        TripModel tripModel = tripDao.findById(idTrip);
        ClientModel clientModel = clientDao.findById(requestModel.getClientesCpf());

        DeliveryServiceModel deliveryService = deliveryServiceDao.findById(idServico);
        if (deliveryService != null) {
            List<ProductModel> productList = productDao.findByService(requestModel.getIdSolicitacao());
            System.out.println("Id solicitacao: " + requestModel.getIdSolicitacao() + "Lista de Produtos: " + productList + ".");
            return ClassMapper.toRequestWithDeliveryDetailDto(requestModel, deliveryService, productList, tripModel, clientModel);
        } else{
        TransportServiceModel transportServiceModel = transportServiceDao.findById(idServico);
        return ClassMapper.toRequestWithTransportDetailDto(requestModel, transportServiceModel, tripModel, clientModel);
        }
    }


    public void insertRequest(RequestDto request) {
        // Lógica de validação ou transformação antes de salvar
        RequestModel requestModel = ClassMapper.toRequestModel(request);
        requestDao.save(requestModel);

    }

//    public RequestModel buscarSolicitacaoPorId(int id) {
//        return requestDao.buscarPorId(id);
//    }
//
//    public boolean atualizarSolicitacao(int id, RequestModel solicitacaoAtualizada) {
//        return requestDao.atualizar(id, solicitacaoAtualizada);
//    }
//
//    public boolean deletarSolicitacao(int id) {
//        return requestDao.deletar(id);
//    }

    public List<RevenueByPaymentKind> getRevenueByPaymentKind() {
        try {
            // Chama o método do DAO para calcular a receita por forma de pagamento
            List<RevenueByPaymentKind> revenueList = requestDao.calcularReceitaPorFormaPagamento();

            // Verifica se a lista está vazia e lança uma exceção, se necessário
            if (revenueList.isEmpty()) {
                throw new IllegalStateException("Nenhuma receita encontrada.");
            }

            return revenueList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao calcular receita por forma de pagamento: " + e.getMessage(), e);
        }
    }

    public Double getReceitaTotalPorAno(int ano) {
        return requestDao.calcularReceitaTotalPorAno(ano);
    }

    public List<MonthlyRequestDto> contarSolicitacoesPorMes(int ano) {
        return requestDao.contarSolicitacoesPorMes(ano);
    }

    public List<TopClientsByRequestsDto> buscarClientesComMaisSolicitacoes() {
        return requestDao.buscarClientesComMaisSolicitacoes();
    }

    public TotalRequestsDto contarTotalSolicitacoes() {
        int total = requestDao.contarTotalSolicitacoes();
        return new TotalRequestsDto(total);
    }

}