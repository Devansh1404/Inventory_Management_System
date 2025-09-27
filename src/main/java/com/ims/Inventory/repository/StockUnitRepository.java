package com.ims.Inventory.repository;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.StockUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockUnitRepository extends JpaRepository<StockUnit, Long> {
    // This new method is required to find the stock record associated with an item
    Optional<StockUnit> findByItem(Item item);
}

