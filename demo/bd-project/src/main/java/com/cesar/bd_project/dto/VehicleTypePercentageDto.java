package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypePercentageDto {
    private String tipoVeiculo;
    private Double percentagem;
}