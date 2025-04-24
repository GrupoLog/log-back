package com.cesar.bd_project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoModel extends VehicleModel{
    private Integer capacidadeCarga;
}
