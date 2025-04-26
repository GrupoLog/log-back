package com.cesar.bd_project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    @NotBlank
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio!")
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    private String rua;

    @NotBlank
    private String bairro;

    @NotNull
    private Integer numero;

    @NotBlank
    private String cidade;
}