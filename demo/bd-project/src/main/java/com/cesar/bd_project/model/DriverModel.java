package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DriverModel {

    private String cnh;

    @JsonProperty("tipo_cnh")
    private String tipoCnh;
    private String cpf;
    private String nome;
    private String tipo;

    @JsonProperty("telefone_um")
    private String telefoneUm;

    @JsonProperty("telefone_dois")
    private String telefoneDois;

    @JsonProperty("cnh_supervisionado")
    private String cnhSupervisionado;
}