package com.cesar.bd_project.controller;

import com.cesar.bd_project.dto.TripDto;
import com.cesar.bd_project.model.TripModel;
import com.cesar.bd_project.response.MessageResponse;
import com.cesar.bd_project.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            List<TripDto> tripList = tripService.listTripWithDetails();
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

    @PostMapping
    public ResponseEntity<?> insertTrip(@Valid @RequestBody TripModel trip) {
        try {
            tripService.insertTrip(trip);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Viagem inserido com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao inserir viagem: " + e.getMessage()));
        }
    }

    @PutMapping("/{id_viagem}")
    public ResponseEntity<MessageResponse> updatetrip(@PathVariable ("id_viagem") Integer idViagem, @Valid @RequestBody TripModel trip) {
        trip.setIdViagem(idViagem);
        try {
            tripService.updateTrip(trip);
            return ResponseEntity.ok(new MessageResponse("Viagem atualizado com sucesso!"));
        } catch (IllegalArgumentException e) {
            //return "Erro de validação: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            //return "Erro ao atualizar produto no banco de dados: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Erro ao atualizar viagem: " + e.getMessage()));
        }
    }
}
