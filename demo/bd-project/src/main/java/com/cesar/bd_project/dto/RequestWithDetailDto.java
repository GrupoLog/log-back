package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestWithDetailDto {
    // RequestModel attributes
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

    // ServiceModel attributes
    @JsonProperty("id_viagem")
    @NotNull(message = "Id viagem não pode ser nulo/vazia")
    private Integer idViagem;

    // ClientModel attributes
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 30, message = "Nome deve ter no máximo 30 caracteres")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 30, message = "Sobrenome deve ter no máximo 30 caracteres")
    private String sobrenome;

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 30, message = "Rua deve ter no máximo 30 caracteres")
    private String rua;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 30, message = "Bairro deve ter no máximo 30 caracteres")
    private String bairro;

    @NotNull(message = "Número é obrigatório")
    @Min(value = 1, message = "Número deve ser maior que zero")
    private Integer numero;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 30, message = "Cidade deve ter no máximo 30 caracteres")
    private String cidade;
}
