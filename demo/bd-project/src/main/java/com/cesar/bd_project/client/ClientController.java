package com.cesar.bd_project.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public String cadastrarCliente(@RequestBody ClientModel cliente) {
        try {
            clienteService.cadastrarCliente(cliente);
            return "Cliente cadastrado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao salvar cliente no banco de dados: " + e.getMessage();
        }
    }


    @GetMapping
    public List<ClientModel> listarClientes() {
        try {
            return clienteService.listarClientes();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }
    }


    @GetMapping("/{cpf}")
    public ClientModel buscarClientePorCpf(@PathVariable String cpf) {
        try {
            return clienteService.buscarClientePorCpf(cpf);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }
    }


    @PutMapping
    public String atualizarCliente(@RequestBody ClientModel cliente) {
        try {
            clienteService.atualizarCliente(cliente);
            return "Cliente atualizado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao atualizar cliente no banco de dados: " + e.getMessage();
        }
    }

    
    @DeleteMapping("/{cpf}")
    public String deletarCliente(@PathVariable String cpf) {
        try {
            clienteService.deletarCliente(cpf);
            return "Cliente deletado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao deletar cliente no banco de dados: " + e.getMessage();
        }
    }
}
