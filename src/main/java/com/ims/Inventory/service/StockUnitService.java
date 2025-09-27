package com.ims.Inventory.service;

import com.ims.Inventory.model.StockUnit;
import java.util.List;

public interface StockUnitService {

    /**
     * Retrieves all stock units from the database.
     * @return a list of all StockUnit objects.
     */
    List<StockUnit> getAllStockUnits();

    /**
     * Updates the quantity of a specific stock unit.
     * @param stockUnitId The ID of the stock unit to update.
     * @param newQuantity The new quantity to set.
     */
    void updateStockQuantity(Long stockUnitId, int newQuantity);
}

