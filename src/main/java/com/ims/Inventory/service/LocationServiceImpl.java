package com.ims.Inventory.service;

import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.LocationType; // <-- Add import
import com.ims.Inventory.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    // --- THIS IS THE NEW METHOD IMPLEMENTATION ---
    @Override
    public List<Location> getLocationsByType(LocationType type) {
        return locationRepository.findByType(type);
    }
}