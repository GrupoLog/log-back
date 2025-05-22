package com.cesar.bd_project.mapper;

import com.cesar.bd_project.dto.ClientWithPhoneDto;
import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.dto.RequestWithDeliveryDetailDto;
import com.cesar.bd_project.dto.RequestWithTransportDetailDto;
import com.cesar.bd_project.model.*;

import java.util.ArrayList;
import java.util.List;

public class ClassMapper {

    public static ClientWithPhoneDto toClientWithPhoneDto(ClientModel client, List<PhoneModel> phoneList) {

        List<String> cleanPhoneList = new ArrayList<>();
        for(PhoneModel phone : phoneList){
            cleanPhoneList.add(phone.getTelefone());
        }

        ClientWithPhoneDto clientWithPhoneDto = new ClientWithPhoneDto();
        clientWithPhoneDto.setCpf(client.getCpf());
        clientWithPhoneDto.setNome(client.getNome());
        clientWithPhoneDto.setSobrenome(client.getSobrenome());
        clientWithPhoneDto.setRua(client.getRua());
        clientWithPhoneDto.setBairro(client.getBairro());
        clientWithPhoneDto.setNumero(client.getNumero());
        clientWithPhoneDto.setCidade(client.getCidade());
        clientWithPhoneDto.setPhonesList(cleanPhoneList);

        return clientWithPhoneDto;
    }

    public static ClientModel toClientModel (ClientWithPhoneDto clientWithPhoneDto) {
        ClientModel client = new ClientModel();
        client.setCpf(clientWithPhoneDto.getCpf());
        client.setNome(clientWithPhoneDto.getNome());
        client.setSobrenome(clientWithPhoneDto.getSobrenome());
        client.setRua(clientWithPhoneDto.getRua());
        client.setBairro(clientWithPhoneDto.getBairro());
        client.setNumero(clientWithPhoneDto.getNumero());
        client.setCidade(clientWithPhoneDto.getCidade());

        return client;
    }

    public static List<PhoneModel> toPhoneModel(ClientWithPhoneDto clientWithPhoneDto) {
        List<PhoneModel> phoneList = new ArrayList<>();

        for (String phoneNumber : clientWithPhoneDto.getPhonesList()) {
            PhoneModel phone = new PhoneModel();
            phone.setClientesCpf(clientWithPhoneDto.getCpf());
            phone.setTelefone(phoneNumber);
            phoneList.add(phone);
        }
        return phoneList;
    }

    public static RequestDto toRequestDto (RequestModel requestModel) {
        RequestDto request = new RequestDto();
        request.setIdSolicitacao(requestModel.getIdSolicitacao());
        request.setDataSolicitacao(requestModel.getDataSolicitacao());
        request.setFormaPagamento(requestModel.getFormaPagamento());
        request.setValorPagamento(requestModel.getValorPagamento());
        request.setClientesCpf(requestModel.getClientesCpf());

        return request;
    }

    public static RequestWithTransportDetailDto toRequestWithTransportDetailDto(RequestModel requestModel, TransportServiceModel transportServiceModel, TripModel tripModel, ClientModel clientModel) {

        RequestWithTransportDetailDto dto = new RequestWithTransportDetailDto();

        // Map RequestModel attributes
        dto.setIdSolicitacao(requestModel.getIdSolicitacao());
        dto.setDataSolicitacao(requestModel.getDataSolicitacao());
        dto.setFormaPagamento(requestModel.getFormaPagamento());
        dto.setValorPagamento(requestModel.getValorPagamento());
        dto.setClientesCpf(requestModel.getClientesCpf());
        dto.setIdServico(requestModel.getIdServico());


        dto.setQtdPassageiros(transportServiceModel.getQtdPassageiros());
        dto.setDescricaoTransporte(transportServiceModel.getDescricaoTransporte());


        // Map TripModel attributes
        dto.setIdViagem(tripModel.getIdViagem());
        dto.setDataViagem(tripModel.getDataViagem());
        dto.setHoraViagem(tripModel.getHoraViagem());
        dto.setOrigem(tripModel.getOrigem());
        dto.setDestino(tripModel.getDestino());
        dto.setVeiculoChassi(tripModel.getVeiculoChassi());
        dto.setMotoristasCnh(tripModel.getMotoristasCnh());

        // Map ClientModel attributes
        dto.setCpf(clientModel.getCpf());
        dto.setNome(clientModel.getNome());
        dto.setSobrenome(clientModel.getSobrenome());
        dto.setRua(clientModel.getRua());
        dto.setBairro(clientModel.getBairro());
        dto.setNumero(clientModel.getNumero());
        dto.setCidade(clientModel.getCidade());

        return dto;
    }

    public static RequestWithDeliveryDetailDto toRequestWithDeliveryDetailDto(RequestModel requestModel, DeliveryServiceModel deliveryServiceModel, List<ProductModel> productList, TripModel tripModel, ClientModel clientModel) {

        RequestWithDeliveryDetailDto dto = new RequestWithDeliveryDetailDto();

        // Map RequestModel attributes
        dto.setIdSolicitacao(requestModel.getIdSolicitacao());
        dto.setDataSolicitacao(requestModel.getDataSolicitacao());
        dto.setFormaPagamento(requestModel.getFormaPagamento());
        dto.setValorPagamento(requestModel.getValorPagamento());
        dto.setClientesCpf(requestModel.getClientesCpf());
        dto.setIdServico(requestModel.getIdServico());

        dto.setPesoTotal(deliveryServiceModel.getPesoTotal());
        dto.setDescricaoProduto(deliveryServiceModel.getDescricaoProduto());

        // Map TripModel attributes
        dto.setIdViagem(tripModel.getIdViagem());
        dto.setDataViagem(tripModel.getDataViagem());
        dto.setHoraViagem(tripModel.getHoraViagem());
        dto.setOrigem(tripModel.getOrigem());
        dto.setDestino(tripModel.getDestino());
        dto.setVeiculoChassi(tripModel.getVeiculoChassi());
        dto.setMotoristasCnh(tripModel.getMotoristasCnh());

        // Map ClientModel attributes
        dto.setCpf(clientModel.getCpf());
        dto.setNome(clientModel.getNome());
        dto.setSobrenome(clientModel.getSobrenome());
        dto.setRua(clientModel.getRua());
        dto.setBairro(clientModel.getBairro());
        dto.setNumero(clientModel.getNumero());
        dto.setCidade(clientModel.getCidade());

        dto.setProductList(productList);

        return dto;
    }

}
