package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private int peso;

    @JsonProperty("data_validade")
    private LocalDate dataValidade;

    private String descricao;

}
