package com.cesar.bd_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cesar.bd_project.service.ClientService;
import com.cesar.bd_project.client.MessageResponse;
import com.cesar.bd_project.model.ClientModel;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientModel> listClients() {
        try {
            return clientService.listClients();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertClient(@RequestBody ClientModel client) {
        try {
            ClientModel savedClient = clientService.insertClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar cliente no banco de dados: " + e.getMessage());
        }
    }


    @GetMapping("/{cpf}")
    public ClientModel findById(@PathVariable String cpf) {
        try {
            return clientService.findById(cpf);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }
    }


    @PutMapping("/{cpf}")
    public ResponseEntity<MessageResponse> updateClient(@PathVariable String cpf, @RequestBody ClientModel client) {
        try {
            clientService.updateClient(client);
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
    public String deleteClient(@PathVariable String cpf) {
        try {
            clientService.deleteClient(cpf);
            return "Cliente deletado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro de validação: " + e.getMessage();
        } catch (SQLException e) {
            return "Erro ao deletar cliente no banco de dados: " + e.getMessage();
        }
    }
}
