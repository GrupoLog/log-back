package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneModel {

    @NotBlank
    private String telefone;

    @JsonProperty("clientes_cpf")
    @NotBlank
    private String clientesCpf;
}
