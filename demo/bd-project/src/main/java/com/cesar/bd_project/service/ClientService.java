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

    public void updateClient(ClientWithPhoneDto clientWithPhone) {

        ClientModel existingClient = clientDao.findById(clientWithPhone.getCpf());
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente não encontrado com o CPF fornecido.");
        }
        clientDao.update(ClassMapper.toClientModel(clientWithPhone));

        List<PhoneModel> newPhones = ClassMapper.toPhoneModel(clientWithPhone);
        for (PhoneModel phone : newPhones) {
            String telefone = phone.getTelefone();
            PhoneModel existingCpf = phoneDao.findById(telefone);
            if (existingCpf != null && !existingCpf.getClientesCpf().equals(clientWithPhone.getCpf())) {
                throw new IllegalArgumentException("Telefone já cadastrado para outro CPF.");
            }
        }

        phoneDao.deleteByCpf(clientWithPhone.getCpf());
        for (PhoneModel phone : newPhones) {
            phoneDao.save(phone);
        }
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