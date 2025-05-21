package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyServiceCountDto {

    @JsonProperty("mes")
    private int mes;

    @JsonProperty("tipo_servico")
    private String tipoServico;

    @JsonProperty("quantidade")
    private int quantidade;
}
