package com.cesar.bd_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageTicketDto {

    @JsonProperty("ticket_medio")
    private double ticketMedio;
}
