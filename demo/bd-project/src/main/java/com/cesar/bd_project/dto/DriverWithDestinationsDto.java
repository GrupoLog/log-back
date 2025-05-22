package com.cesar.bd_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverWithDestinationsDto {
    private String nome;
    private List<DestinationCount> destinos = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DestinationCount {
        private String destino;
        private Integer totalViagens;
    }
    
    // Convenience method to add a destination
    public void addDestino(String destino, Integer totalViagens) {
        this.destinos.add(new DestinationCount(destino, totalViagens));
    }
}