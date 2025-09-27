package com.ims.Inventory.service;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.StockUnit;
import com.ims.Inventory.repository.ItemRepository;
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

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Saves a new item and creates its initial stock record.
     */
    @Override
    @Transactional
    public Item saveItem(Item item) {
        boolean isNewItem = item.getId() == null;
        Item savedItem = itemRepository.save(item);

        if (isNewItem) {
            StockUnit stockUnit = new StockUnit();
            stockUnit.setItem(savedItem);
            // Use the initial quantity from the form, defaulting to 0 if not provided.
            stockUnit.setQuantity(item.getInitialQuantity() != null ? item.getInitialQuantity() : 0);
            stockUnitRepository.save(stockUnit);
        }
        return savedItem;
    }

    /**
     * Updates an existing item's details.
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
     * Deletes an item and its associated stock unit. The @Transactional annotation ensures
     * that both operations succeed or neither do, maintaining data consistency.
     */
    @Override
    @Transactional
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        // Important: Find and delete the associated stock unit first to prevent database errors.
        stockUnitRepository.findByItem(item).ifPresent(stockUnit -> stockUnitRepository.deleteById(stockUnit.getId()));
        
        // Now, it's safe to delete the item itself.
        itemRepository.deleteById(id);
    }
}

