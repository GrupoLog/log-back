package com.cesar.bd_project.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VanModel extends VehicleModel{

    @JsonProperty("cap_passageiros")
    private Integer capacidadePassageiros;
}
