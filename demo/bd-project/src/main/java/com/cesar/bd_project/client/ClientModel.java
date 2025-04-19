package com.cesar.bd_project.client;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    private String cpf;
    private String nome;
    private String sobrenome;
    private String rua;
    private String bairro;
    private int numero;
    private String cidade;
}