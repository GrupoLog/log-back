package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import com.cesar.bd_project.dao.PhoneDao;
import com.cesar.bd_project.dto.ClientWithPhoneDto;
import com.cesar.bd_project.mapper.ClassMapper;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;
import com.cesar.bd_project.model.ClientModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientDao clientDao;
    private final PhoneDao phoneDao;

    public ClientService(ClientDao clientDao, PhoneDao phoneDao) {
        this.clientDao = clientDao;
        this.phoneDao = phoneDao;

    }

    public List<ClientModel> listClients() {
        try {
            List<ClientModel> clientList = clientDao.list();
            if (clientList.isEmpty()) {
                throw new IllegalStateException("Nenhum cliente encontrado.");
            }

            return clientList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
    }

    public List<ClientWithPhoneDto> listClientsWithPhone() {
        List<ClientModel> clientsList = clientDao.list();
        List<ClientWithPhoneDto> clientsWithPhoneList = new ArrayList<>();

        for (ClientModel client : clientsList) {
            List<PhoneModel> phoneList = phoneDao.findByCpf(client.getCpf());
            ClientWithPhoneDto clientWithPhone = ClassMapper.toClientWithPhoneDto(client, phoneList);

            clientsWithPhoneList.add(clientWithPhone);
        }

        return clientsWithPhoneList;
    }

    public ClientWithPhoneDto findById(String cpf) {
        ClientModel client = clientDao.findById(cpf);
        if(client == null){
            throw new IllegalArgumentException("Cliente não cadastrado com esse CPF!");
        }

        List<PhoneModel> phoneList = phoneDao.findByCpf(client.getCpf());

        return ClassMapper.toClientWithPhoneDto(client, phoneList);
    }

    public void insertClientWithPhone(ClientWithPhoneDto clientWithPhone) {
        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(clientWithPhone.getCpf());
        if (existingClient != null) {
            throw new IllegalArgumentException("Cliente já cadastrado com esse CPF!");
        }
        clientDao.save(ClassMapper.toClientModel(clientWithPhone));
        for (PhoneModel phone : ClassMapper.toPhoneModel(clientWithPhone)){
            phoneDao.save(phone);
        }
    }

    public void updateClient(ClientModel client) {
        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(client.getCpf());
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + client.getCpf() + " não encontrado.");
        }
        clientDao.update(client);
    }

    public void deleteClient(String cpf) {
        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(cpf);
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + cpf + " não encontrado.");
        }
        clientDao.delete(cpf);
    }


}