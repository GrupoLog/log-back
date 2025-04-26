package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    @JsonProperty("id_produto")
    @NotNull
    private Integer idProduto;

    @NotNull
    private Integer peso;

    @JsonProperty("data_validade")
    private LocalDate dataValidade;

    private String descricao;

}
