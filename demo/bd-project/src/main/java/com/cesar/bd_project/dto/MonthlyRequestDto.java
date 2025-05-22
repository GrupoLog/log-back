package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRequestDto {

    @JsonProperty("mes")
    private String mes; 

    @JsonProperty("qtd_solicitacoes")
    private int quantidade;
}
