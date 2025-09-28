package com.ims.Inventory;

import com.ims.Inventory.model.Location;
import com.ims.Inventory.model.LocationType;
import com.ims.Inventory.repository.LocationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private LocationRepository locationRepository;

    @PostConstruct
    public void initializeData() {
        // This method runs after the application starts.
        // We check if the location table is empty.
        if (locationRepository.count() == 0) {
            System.out.println("No locations found, creating default data...");

            // Create a default warehouse
            Location warehouse = new Location();
            warehouse.setName("Main Warehouse");
            warehouse.setAddress("123 Shipping Lane");
            warehouse.setType(LocationType.WAREHOUSE);
            locationRepository.save(warehouse);

            // Create a default retail store
            Location store = new Location();
            store.setName("Downtown Store");
            store.setAddress("456 Retail Ave");
            store.setType(LocationType.RETAIL_STORE);
            locationRepository.save(store);

            System.out.println("Default locations created.");
        }
    }
}