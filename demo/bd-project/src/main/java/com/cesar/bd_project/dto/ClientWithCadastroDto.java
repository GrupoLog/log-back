package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientWithCadastroDto {
    private String cpf;
    private String nome;
    private LocalDate dataCadastro;
}
