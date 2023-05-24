package com.sidarau.parking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sidarau.parking.models.ParkingSpotModel;
import com.sidarau.parking.repositories.ParkingSpotRepository;

import jakarta.transaction.Transactional;

@Service
public class ParkingSpotService {

    @Autowired
    public ParkingSpotRepository repo;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return repo.save(parkingSpotModel);
    }
    
}
