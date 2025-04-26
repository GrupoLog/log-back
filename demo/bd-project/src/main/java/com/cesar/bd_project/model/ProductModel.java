package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @JsonProperty("id_produto")
    private Integer idProduto;

    @NotNull(message = "Peso é obrigatório")
    @Min(value = 1, message = "Peso deve ser maior que zero")
    private Integer peso;

    @JsonProperty("data_validade")
    private LocalDate dataValidade;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;
}
