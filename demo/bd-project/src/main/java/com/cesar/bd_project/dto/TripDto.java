package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    @JsonProperty("id_viagem")
    private int idViagem;

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

    @NotBlank(message = "A placa não pode ser vazia")
    @Size(min = 7, max = 7, message = "Placa deve ter 7 dígitos.")
    private String placaVeiculo;

    @NotBlank(message = "Nome não pode ser vazio!")
    private String nomeMotorista;

}



