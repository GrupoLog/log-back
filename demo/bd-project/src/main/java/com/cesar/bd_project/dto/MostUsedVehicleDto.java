package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostUsedVehicleDto {

    @JsonProperty("chassi")
    private String chassi;

    @JsonProperty("placa")
    private String placa;

    @JsonProperty("qtd_viagens")
    private int totalViagens;
}
