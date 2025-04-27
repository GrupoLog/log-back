package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverModel {
    @NotBlank(message = "CNH não pode ser vazia!")
    @Size(min = 11, max = 11, message = "CNH deve ter 11 dígitos.")
    private String cnh;

    @JsonProperty("tipo_cnh")
    @NotBlank(message = "Tipo da CNH não pode ser vazio!")
    @Pattern(regexp = "A|B|AB|C|D|E", message = "Tipo da CNH deve ser um dos seguintes: A, B, AB, C, D ou E.")
    private String tipoCnh;

    @NotBlank(message = "CPF não pode ser vazio!")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos.")
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio!")
    private String nome;

    @NotBlank(message = "Tipo de motorista não pode ser vazio!")
    @Pattern(regexp = "Fixo|Terceirizado", message = "Tipo deve ser 'Fixo' ou 'Terceirizado'.")
    private String tipo;

    @JsonProperty("telefone_um")
    @NotBlank(message = "Telefone um não pode ser vazio!")
    @Size(min = 11, max = 11, message = "Telefone um deve ter 11 dígitos.")
    private String telefoneUm;

    @JsonProperty("telefone_dois")
    @Pattern(
            regexp = "^$|\\d{11}$",
            message = "Telefone dois deve ter 11 dígitos ou ser vazia."
    )
    private String telefoneDois;

    @JsonProperty("cnh_supervisionado")
    @Pattern(
            regexp = "^$|\\d{11}$",
            message = "CNH do supervisionado deve ter exatamente 11 dígitos ou ser vazia."
    )
    private String cnhSupervisionado;
}