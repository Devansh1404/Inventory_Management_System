package com.ims.Inventory.service;

import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.LocationType; // <-- Add import
import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAllLocations();
    Location saveLocation(Location location);
    Optional<Location> getLocationById(Long id);
    void deleteLocation(Long id);

    // --- THIS IS THE NEW METHOD TO ADD ---
    List<Location> getLocationsByType(LocationType type);
}