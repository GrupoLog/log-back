package com.cesar.bd_project.controller;

import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/viagens")
@Validated
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<?> listTrips() {
        try {
            List<TripModel> tripList = tripService.listTrips();
            return ResponseEntity.ok(tripList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar viagens: " + e.getMessage());
        }
    }

    @GetMapping("/{id_viagem}")
    public ResponseEntity<?> findById(@PathVariable ("id_viagem") Integer idViagem) {
        try {
            TripModel trip = tripService.findById(idViagem);
            if (trip != null) {
                return ResponseEntity.ok(trip);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar viagem: " + e.getMessage());
        }
    }
}
