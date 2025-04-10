package com.cesar.bd_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    private int id_produto;
    private int peso;
    private Date data_validade;
    private String descricao;

}
