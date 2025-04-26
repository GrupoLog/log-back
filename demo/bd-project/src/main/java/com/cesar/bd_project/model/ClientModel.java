package com.cesar.bd_project.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 30, message = "Nome deve ter no máximo 30 caracteres")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 30, message = "Sobrenome deve ter no máximo 30 caracteres")
    private String sobrenome;

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 30, message = "Rua deve ter no máximo 30 caracteres")
    private String rua;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 30, message = "Bairro deve ter no máximo 30 caracteres")
    private String bairro;

    @NotNull(message = "Número é obrigatório")
    @Min(value = 1, message = "Número deve ser maior que zero")
    private Integer numero;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 30, message = "Cidade deve ter no máximo 30 caracteres")
    private String cidade;
}