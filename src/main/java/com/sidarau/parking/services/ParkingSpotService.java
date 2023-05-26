package com.sidarau.parking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sidarau.parking.models.ParkingSpotModel;
import com.sidarau.parking.repositories.ParkingSpotRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    public ParkingSpotRepository repo;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return repo.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return repo.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repo.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return repo.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpotModel> Listar() {
        return repo.findAll();
    }

    public ParkingSpotModel Obter(UUID id) {
        return repo.findOne(id);
    }
}
