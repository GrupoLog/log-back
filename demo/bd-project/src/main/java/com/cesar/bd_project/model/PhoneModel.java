package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneModel {
    private String telefone;

    @JsonProperty("clientes_cpf")
    private String clientesCpf;
}
