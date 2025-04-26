package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneModel {
    @NotBlank(message = "Telefone não pode ser vazio!")
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 dígitos.")
    private String telefone;

    @JsonProperty("clientes_cpf")
    @NotBlank(message = "CPF do cliente é obrigatório!")
    @Size(min = 11, max = 11, message = "CPF deve conter 11 dígitos.")
    private String clientesCpf;
}
