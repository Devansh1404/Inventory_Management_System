package com.ims.Inventory.service;

import com.ims.Inventory.model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> getAllItems();
    
    Optional<Item> getItemById(Long id);

    Item saveItem(Item item);
    
    Item updateItem(Item itemDetails); 

    void deleteItem(Long id); 
}
