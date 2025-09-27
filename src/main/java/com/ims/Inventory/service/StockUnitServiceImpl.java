package com.ims.Inventory.service;

import com.ims.Inventory.model.StockUnit;
import com.ims.Inventory.repository.StockUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockUnitServiceImpl implements StockUnitService {

    @Autowired
    private StockUnitRepository stockUnitRepository;

    /**
     * Retrieves all stock units from the database.
     */
    @Override
    public List<StockUnit> getAllStockUnits() {
        return stockUnitRepository.findAll();
    }

    /**
     * Finds a stock unit by its ID, updates its quantity, and saves it.
     * Throws an error if the stock unit ID is not found.
     */
    @Override
    public void updateStockQuantity(Long stockUnitId, int newQuantity) {
        // Find the stock unit by its ID, otherwise throw an exception
        StockUnit stockUnit = stockUnitRepository.findById(stockUnitId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Stock Unit ID: " + stockUnitId));

        // Set the new quantity and save the changes to the database
        stockUnit.setQuantity(newQuantity);
        stockUnitRepository.save(stockUnit);
    }
}

