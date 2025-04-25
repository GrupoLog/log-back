package com.cesar.bd_project.controller;

import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.model.PhoneModel;
import com.cesar.bd_project.service.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService){
        this.phoneService = phoneService;
    }

    @GetMapping
    public ResponseEntity<?> listPhones() {
        try {
            List<PhoneModel> phoneList = phoneService.listPhones();
            return ResponseEntity.ok(phoneList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar telefones: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insertPhone(@RequestBody PhoneModel phone) {
        try {
            phoneService.insertPhone(phone);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Telefone inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir telefone: " + e.getMessage()));
        }
    }

    @PutMapping("/{clientes_cpf}")
    public ResponseEntity<MessageResponse> updatePhone(@PathVariable ("clientes_cpf") String clientesCpf, @RequestBody PhoneModel phone) {
        phone.setClientesCpf(clientesCpf);
        try {
            phoneService.updatePhone(phone);
            return ResponseEntity.ok(new MessageResponse("Telefone atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar telefone: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{telefone}")
    public ResponseEntity<MessageResponse> deletePhone(@PathVariable String telefone) {
        try {
            phoneService.deletePhone(telefone);
            return ResponseEntity.ok(new MessageResponse("Telefone deletado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao deletar telefone: " + e.getMessage()));
        }
    }

}
