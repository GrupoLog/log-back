package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripWithDetailDto {
    // Viagem
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

    // Motorista
    @NotBlank(message = "CNH não pode ser vazia!")
    @Size(min = 11, max = 11, message = "CNH deve ter 11 dígitos.")
    private String cnh;

    @NotBlank(message = "Nome não pode ser vazio!")
    private String nome;

    @NotBlank(message = "Tipo de motorista não pode ser vazio!")
    @Pattern(regexp = "Fixo|Terceirizado", message = "Tipo deve ser 'Fixo' ou 'Terceirizado'.")
    private String tipo;

    @JsonProperty("tipo_cnh")
    @NotBlank(message = "Tipo da CNH não pode ser vazio!")
    @Pattern(regexp = "A|B|AB|C|D|E", message = "Tipo da CNH deve ser um dos seguintes: A, B, AB, C, D ou E.")
    private String tipoCnh;

    @JsonProperty("telefone_um")
    @NotBlank(message = "Telefone um não pode ser vazio!")
    @Size(min = 11, max = 11, message = "Telefone um deve ter 11 dígitos.")
    private String telefoneUm;

    // Veículo
    @NotBlank(message = "A placa não pode ser vazia")
    @Size(min = 7, max = 7, message = "Placa deve ter 7 dígitos.")
    private String placa;

    @NotBlank(message = "Chassi não pode ser vazio")
    @Size(min = 17, max = 17, message = "Placa deve ter 7 dígitos.")
    private String chassi;

    @NotBlank(message = "Proprietario não pode ser vazio")
    @Pattern(regexp = "Proprio|Terceirizado", message = "Tipo deve ser 'Proprio' ou 'Terceirizado'.")
    private String proprietario;

}