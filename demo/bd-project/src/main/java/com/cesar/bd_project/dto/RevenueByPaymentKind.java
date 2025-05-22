package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueByPaymentKind {

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @JsonProperty("receita")
    private Double receita;
}