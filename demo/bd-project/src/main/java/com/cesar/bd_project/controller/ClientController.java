package com.cesar.bd_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cesar.bd_project.client.ClienteService;
import com.cesar.bd_project.client.MessageResponse;
import com.cesar.bd_project.model.ClientModel;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody ClientModel cliente) {
        try {
            ClientModel clienteSalvo = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar cliente no banco de dados: " + e.getMessage());
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


    @PutMapping("/{cpf}")
    public ResponseEntity<MessageResponse> atualizarCliente(@PathVariable String cpf, @RequestBody ClientModel cliente) {
        try {
            clienteService.atualizarCliente(cliente);
            return ResponseEntity.ok(new MessageResponse("Cliente atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            //return "Erro de validação: " + e.getMessage();
            return ResponseEntity.ok(new MessageResponse("Erro de validação!"));
        } catch (SQLException e) {
            //return "Erro ao atualizar cliente no banco de dados: " + e.getMessage();
            return ResponseEntity.ok(new MessageResponse("Erro ao atualizar cliente no banco de dados"));

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
