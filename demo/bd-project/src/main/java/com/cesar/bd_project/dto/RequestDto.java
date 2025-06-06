package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    @JsonProperty("id_solicitacao")
    private int idSolicitacao;

    @JsonProperty("data_solicitacao")
    private LocalDate dataSolicitacao;

    @JsonProperty("forma_pagamento")
    private String formaPagamento;

    @JsonProperty("valor_pagamento")
    private Double valorPagamento;

    @JsonProperty("status_pagamento")
    private String statusPagamento;

    @JsonProperty("clientes_cpf")
    private String clientesCpf;

    @JsonProperty("id_servico")
    private int idServico;


}
