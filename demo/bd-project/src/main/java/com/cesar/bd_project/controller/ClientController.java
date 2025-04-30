package com.cesar.bd_project.controller;

import com.cesar.bd_project.dto.ClientWithPhoneDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cesar.bd_project.service.ClientService;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.model.ClientModel;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated

public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<?> listClientsWithPhone() {
        try {
            List<ClientWithPhoneDto> clientListWithPhone = clientService.listClientsWithPhone();
            return ResponseEntity.ok(clientListWithPhone);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findById(@PathVariable String cpf) {
        try {
            ClientWithPhoneDto clientWithPhone = clientService.findById(cpf);
            if (clientWithPhone != null) {
                return ResponseEntity.ok(clientWithPhone);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertClientWithPhone(@Valid @RequestBody ClientWithPhoneDto clientWithPhone) {
        try {
            clientService.insertClientWithPhone(clientWithPhone);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Cliente inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir cliente: " + e.getMessage()));
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<MessageResponse> updateClient(@PathVariable String cpf, @Valid @RequestBody ClientWithPhoneDto clientWithPhone) {
        clientWithPhone.setCpf(cpf);
        try {
            clientService.updateClient(clientWithPhone);
            return ResponseEntity.ok(new MessageResponse("Cliente atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar cliente: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<MessageResponse> deleteClient(@PathVariable String cpf) {
        try {
            clientService.deleteClient(cpf);
            return ResponseEntity.ok(new MessageResponse("Cliente deletado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar cliente: " + e.getMessage()));
        }
    }
}
