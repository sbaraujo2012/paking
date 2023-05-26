package com.sidarau.parking.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sidarau.parking.dtos.ParkingSpotDto;
import com.sidarau.parking.models.ParkingSpotModel;
import com.sidarau.parking.services.ParkingSpotService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService service;
    
    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;        
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> Listar(){
        return ResponseEntity.status(HttpStatus.OK).body(service.Listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> Obter(@PathVariable(value = "id")UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional  = Optional.ofNullable(service.Obter(id));
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(parkingSpotModelOptional.get()));
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto dto) {
        if(service.existsByLicensePlateCar(dto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }

        if(service.existsByParkingSpotNumber(dto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }

        if(service.existsByApartmentAndBlock(dto.getApartment(), dto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "Conflict: Parking Spot already registered for this Apartment/Block!");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(dto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parkingSpotModel));
    }



}
