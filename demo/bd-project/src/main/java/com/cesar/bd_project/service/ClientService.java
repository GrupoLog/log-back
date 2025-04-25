package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import org.springframework.stereotype.Service;
import com.cesar.bd_project.model.ClientModel;

import java.util.List;

@Service
public class ClientService {

    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
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

    public ClientModel findById(String cpf) {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        return clientDao.findById(cpf);
    }

    public void insertClient(ClientModel client) {
        // Validação
        if (client.getNome() == null || client.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        clientDao.save(client);
    }

    public void updateClient(ClientModel client) {
        // Verifica se o CPF é nulo ou vazio
        if (client.getCpf() == null || client.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(client.getCpf());
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + client.getCpf() + " não encontrado.");
        }
        clientDao.update(client);
    }

    public void deleteClient(String cpf) {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = clientDao.findById(cpf);
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + cpf + " não encontrado.");
        }

        clientDao.delete(cpf);
    }


}