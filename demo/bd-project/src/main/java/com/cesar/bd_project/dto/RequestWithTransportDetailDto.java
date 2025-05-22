package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestWithTransportDetailDto {

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

    @JsonProperty("qtd_passageiros")
    private Integer qtdPassageiros;

    @JsonProperty("descricao_transporte")
    private String descricaoTransporte;


    // TripModel attributes
    @JsonProperty("id_viagem")
    @NotNull(message = "Id viagem não pode ser nulo/vazia")
    private Integer idViagem;

    @JsonProperty("data_viagem")
    @NotNull(message = "A data da viagem não pode ser nula.")
    private LocalDate dataViagem;

    @JsonProperty("hora_viagem")
    @NotNull(message = "A hora da viagem não pode ser nula.")
    private LocalTime horaViagem;

    @NotBlank(message = "A origem não pode ser vazia.")
    private String origem;

    @NotBlank(message = "O destino não pode ser vazio.")
    private String destino;

    @JsonProperty("veiculo_chassi")
    @NotBlank
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Chassi deve ter 17 dígitos.")
    private String veiculoChassi;

    @JsonProperty("motoristas_cnh")
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CNH deve ter 11 dígitos.")
    private String motoristasCnh;

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
