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
public class CanDriveModel {

    @JsonProperty("motorista_cnh")
    @NotBlank(message = "CNH não pode ser vazia!")
    @Size(min = 11, max = 11, message = "CNH deve ter 11 dígitos.")
    private String motoristaCnh;

    @JsonProperty("veiculo_chassi")
    @NotBlank
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Chassi deve ter 17 dígitos.")
    private String veiculoChassi;
}
