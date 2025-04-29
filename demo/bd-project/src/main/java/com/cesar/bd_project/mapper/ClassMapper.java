package com.cesar.bd_project.mapper;

import com.cesar.bd_project.dto.ClientWithPhoneDto;
import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.model.PhoneModel;

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
}
