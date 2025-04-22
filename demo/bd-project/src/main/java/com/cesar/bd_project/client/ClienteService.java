package com.cesar.bd_project.client;

import org.springframework.stereotype.Service;

import com.cesar.bd_project.dao.ClientDAO;
import com.cesar.bd_project.model.ClientModel;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClienteService {

    private final ClientDAO dao;

    // Injeção de dependência via construtor
    public ClienteService(ClientDAO dao) {
        this.dao = dao;
    }

    public ClientModel cadastrarCliente(ClientModel cliente) throws SQLException {
        // Validação
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    
        // Salvar e retornar
        return dao.salvar(cliente);
    }
    

    public List<ClientModel> listarClientes() throws SQLException {
        return dao.listarTodos();
    }

    public ClientModel buscarClientePorCpf(String cpf) throws SQLException {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            System.err.println("CPF não pode ser nulo ou vazio");
            return null;
        }
        return dao.buscarPorCpf(cpf);
    }

    public void atualizarCliente(ClientModel cliente) throws SQLException {
        // Verifica se o CPF é nulo ou vazio
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = dao.buscarPorCpf(cliente.getCpf());
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + cliente.getCpf() + " não encontrado.");
        }
        dao.atualizar(cliente);
    }

    public void deletarCliente(String cpf) throws SQLException {
        // Verifica se o CPF é nulo ou vazio
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        ClientModel existingClient = dao.buscarPorCpf(cpf);
        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente com o CPF " + cpf + " não encontrado.");
        }

        dao.deletar(cpf);
    }


}