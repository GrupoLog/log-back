package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransportServiceModel extends ServiceModel{

    @JsonProperty("qtd_passageiros")
    private Integer qtdPassageiros;

    @JsonProperty("descricao_transporte")
    private String descricaoTransporte;
}
