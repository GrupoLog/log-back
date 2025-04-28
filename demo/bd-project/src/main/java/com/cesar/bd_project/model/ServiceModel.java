package com.cesar.bd_project.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {

    @JsonProperty("id_servico")
    private Integer idServico;

    @JsonProperty("id_viagem")
    @NotNull(message = "Id viagem não pode ser nulo/vazia")
    private Integer idViagem;
}
