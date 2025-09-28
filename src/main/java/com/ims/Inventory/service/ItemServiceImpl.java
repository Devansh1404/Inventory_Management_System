package com.ims.Inventory.service;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.StockUnit;
import com.ims.Inventory.repository.ItemRepository;
import com.ims.Inventory.repository.LocationRepository;
import com.ims.Inventory.repository.StockUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockUnitRepository stockUnitRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Saves a new item and creates its initial stock record in the user-selected location.
     */
    @Override
    @Transactional
    public Item saveItem(Item item) {
        boolean isNewItem = item.getId() == null;
        Item savedItem = itemRepository.save(item);

        if (isNewItem && item.getInitialQuantity() != null && item.getInitialQuantity() > 0) {
            
            // --- THIS IS THE UPDATED LOGIC ---
            // Get the location ID chosen by the user in the form
            Long locationId = item.getLocationId();
            if (locationId == null) {
                throw new IllegalStateException("Location ID was not provided for the new item.");
            }

            // Find the selected location in the database
            Location selectedLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalStateException("Selected location with ID " + locationId + " not found."));

            StockUnit stockUnit = new StockUnit();
            stockUnit.setItem(savedItem);
            stockUnit.setLocation(selectedLocation); // <-- Set the location object from the form
            stockUnit.setQuantity(item.getInitialQuantity());
            
            stockUnitRepository.save(stockUnit);
        }
        return savedItem;
    }

    /**
     * Updates an existing item's details (name, category, price). Stock is not affected here.
     */
    @Override
    @Transactional
    public Item updateItem(Item itemDetails) {
        Item existingItem = itemRepository.findById(itemDetails.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + itemDetails.getId()));
        
        existingItem.setName(itemDetails.getName());
        existingItem.setCategory(itemDetails.getCategory());
        existingItem.setPrice(itemDetails.getPrice());
        
        return itemRepository.save(existingItem);
    }

    /**
     * Deletes an item and ALL of its associated stock units from every location.
     */
    @Override
    @Transactional
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        List<StockUnit> stockUnitsToDelete = stockUnitRepository.findByItem(item);
        
        if (!stockUnitsToDelete.isEmpty()) {
            stockUnitRepository.deleteAll(stockUnitsToDelete);
        }
        
        itemRepository.deleteById(id);
    }
}