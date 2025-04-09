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
        // Nome não pode ser vazio
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        dao.salvar(cliente);
    }

    public List<ClientModel> listarClientes() throws SQLException {
        return dao.listarTodos();
    }

    public ClientModel buscarClientePorCpf(String cpf) throws SQLException {
        return dao.buscarPorCpf(cpf);
    }

    public void atualizarCliente(ClientModel cliente) throws SQLException {

        dao.atualizar(cliente);
    }

    public void deletarCliente(String cpf) throws SQLException {

        dao.deletar(cpf);
    }


}