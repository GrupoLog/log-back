package com.cesar.bd_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryServiceModel extends ServiceModel{

    @NotBlank(message = "Destinatario não pode ser nulo")
    private String destinatario;

    @JsonProperty("peso_total")
    private Integer pesoTotal;

    @JsonProperty("descricao_produto")
    private String descricaoProduto; 
}
