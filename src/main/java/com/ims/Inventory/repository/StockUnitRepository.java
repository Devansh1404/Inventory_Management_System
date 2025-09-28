package com.ims.Inventory.repository;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.StockUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- Add this import
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockUnitRepository extends JpaRepository<StockUnit, Long> {

    // --- THIS IS THE NEW, EFFICIENT QUERY ---
    @Query("SELECT su FROM StockUnit su JOIN FETCH su.item JOIN FETCH su.location")
    List<StockUnit> findAllWithDetails();

    // --- Existing methods ---
    List<StockUnit> findByItem(Item item);
    Optional<StockUnit> findByItemAndLocation(Item item, Location location);
}