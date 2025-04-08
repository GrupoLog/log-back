package com.cesar.bd_project.client;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClienteService {

    private final ClientDAO dao;

    // Injeção de dependência via construtor
    public ClienteService(ClientDAO dao) {
        this.dao = dao;
    }

    public void cadastrarCliente(ClientModel cliente) throws SQLException {
        // Exemplo de regra: nome não pode ser vazio
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        dao.salvar(cliente);
    }

    public List<ClientModel> listarClientes() throws SQLException {
        return dao.listarTodos();
    }

    // Demais métodos podem ser implementados aqui, como atualizar, deletar, buscarPorId, etc.
}