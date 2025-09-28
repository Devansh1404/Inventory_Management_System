package com.ims.Inventory.repository;

import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.LocationType; // <-- Add import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // <-- Add import

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // --- THIS IS THE NEW METHOD TO ADD ---
    List<Location> findByType(LocationType type);
}