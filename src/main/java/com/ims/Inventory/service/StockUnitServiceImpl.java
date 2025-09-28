package com.ims.Inventory.service;

import com.ims.Inventory.model.StockUnit;
import com.ims.Inventory.repository.StockUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockUnitServiceImpl implements StockUnitService {

    @Autowired
    private StockUnitRepository stockUnitRepository;

    @Override
    public List<StockUnit> getAllStockUnits() {
        // --- THIS IS THE UPDATED LINE ---
        // This uses the new, efficient query to prevent performance issues.
        return stockUnitRepository.findAllWithDetails();
    }

    @Override
    public void updateStockQuantity(Long stockUnitId, int newQuantity) {
        StockUnit stockUnit = stockUnitRepository.findById(stockUnitId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Stock Unit ID: " + stockUnitId));
        
        stockUnit.setQuantity(newQuantity);
        stockUnitRepository.save(stockUnit);
    }
}