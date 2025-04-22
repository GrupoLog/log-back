package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import org.springframework.stereotype.Service;
import com.cesar.bd_project.model.ClientModel;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService {

    private final ClientDao clientDao;

    // Injeção de dependência via construtor
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<ClientModel> listClients() throws SQLException {
        return clientDao.list();
    }

    public ClientModel insertClient(ClientModel client) throws SQLException {
        // Validação
        if (client.getNome() == null || client.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Salvar e retornar
        return clientDao.save(client);
    }


    public ClientModel findById(String cpf) throws SQLException {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            System.err.println("CPF não pode ser nulo ou vazio");
            return null;
        }
        return clientDao.findById(cpf);
    }

    public void updateClient(ClientModel client) throws SQLException {
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

    public void deleteClient(String cpf) throws SQLException {
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