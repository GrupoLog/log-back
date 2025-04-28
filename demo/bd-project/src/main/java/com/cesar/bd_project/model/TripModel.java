package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripModel {

    @JsonProperty("id_viagem")
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
}