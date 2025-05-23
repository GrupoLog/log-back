package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeMonthlyTripsDto {
    private String tipoVeiculo;
    private Integer mes;
    private String nomeMes;
    private Integer ano;
    private Integer quantidadeViagens;
}