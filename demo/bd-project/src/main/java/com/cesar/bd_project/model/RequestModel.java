package com.cesar.bd_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {

    @JsonProperty("id_solicitacao")
    private int idSolicitacao;

    @JsonProperty("data_solicitacao")
    private LocalDate dataSolicitacao;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @JsonProperty("valor_pagamento")
    private Double valorPagamento;

    @JsonProperty("clientes_cpf")
    private String clientesCpf;

    @JsonProperty("id_servico")
    private int idServico;
    
}
