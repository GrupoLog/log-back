package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import com.cesar.bd_project.dao.PhoneDao;
import com.cesar.bd_project.dto.ClientsWithPhoneDto;
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

    public List<ClientsWithPhoneDto> listClientsWithPhone() {
        List<ClientModel> clientsList = clientDao.list();
        List<ClientsWithPhoneDto> clientsWithPhoneList = new ArrayList<>();

        for (ClientModel client : clientsList) {
            List<PhoneModel> phoneList = phoneDao.findByCpf(client.getCpf());

            List<String> cleanPhoneList = new ArrayList<>();

            for(PhoneModel phone : phoneList) {
                cleanPhoneList.add(phone.getTelefone());
            }

            ClientsWithPhoneDto clientWithPhone = new ClientsWithPhoneDto(client, cleanPhoneList);

            clientsWithPhoneList.add(clientWithPhone);
        }

        return clientsWithPhoneList;
    }

    public ClientModel findById(String cpf) {
        return clientDao.findById(cpf);
    }

    public void insertClient(ClientModel client) {
        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(client.getCpf());
        if (existingClient != null) {
            throw new IllegalArgumentException("Cliente já cadastrado com esse CPF!");
        }
        clientDao.save(client);
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