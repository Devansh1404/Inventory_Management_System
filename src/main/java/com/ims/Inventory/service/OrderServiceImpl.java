package com.ims.Inventory.service;

import com.ims.Inventory.model.*; // Use wildcard to import all models
import com.ims.Inventory.repository.LocationRepository;
import com.ims.Inventory.repository.OrderRepository;
import com.ims.Inventory.repository.StockUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockUnitRepository stockUnitRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Order> getAllOrders() {
        // --- THIS IS THE CORRECTED LINE ---
        // It uses the efficient query to prevent the page from failing to load.
        return orderRepository.findAllWithDetails();
    }

    /**
     * Saves a new order, deducting stock based on the chosen fulfillment type.
     */
    @Override
    @Transactional
    public Order saveOrder(Order order) throws InsufficientStockException {
        Item item = order.getItem();
        Location fulfillmentLocation;
        
        // Check the fulfillment type chosen by the user in the form
        if (order.getFulfillmentType() == OrderFulfillmentType.SHIPPING) {
            // If SHIPPING, use the default warehouse (ID=1)
            fulfillmentLocation = locationRepository.findById(1L)
                    .orElseThrow(() -> new IllegalStateException("Default shipping warehouse with ID 1 not found."));

        } else if (order.getFulfillmentType() == OrderFulfillmentType.IN_STORE_PICKUP) {
            // If PICKUP, use the location ID chosen from the dropdown
            Long locationId = order.getFulfillmentLocationId();
            if (locationId == null) {
                throw new IllegalArgumentException("A store location must be selected for in-store pickup.");
            }
            fulfillmentLocation = locationRepository.findById(locationId)
                    .orElseThrow(() -> new IllegalStateException("Selected pickup store with ID " + locationId + " not found."));
        } else {
            throw new IllegalArgumentException("Invalid fulfillment type specified.");
        }

        // Find the stock unit for the item AT THE CORRECT FULFILLMENT LOCATION
        StockUnit stockUnit = stockUnitRepository.findByItemAndLocation(item, fulfillmentLocation)
                .orElseThrow(() -> new InsufficientStockException("Item '" + item.getName() + "' is not in stock at " + fulfillmentLocation.getName() + "."));

        // Check if there is enough quantity in stock at that location
        if (stockUnit.getQuantity() < order.getQuantity()) {
            throw new InsufficientStockException("Not enough stock for " + item.getName() + ". Only " + stockUnit.getQuantity() + " available at " + fulfillmentLocation.getName() + ".");
        }

        // Decrease the stock quantity at the correct location
        stockUnit.setQuantity(stockUnit.getQuantity() - order.getQuantity());
        stockUnitRepository.save(stockUnit);

        // Finalize the order details before saving
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(item.getPrice() * order.getQuantity());
        order.setFulfillmentLocation(fulfillmentLocation); // Save the actual location to the order
        
        return orderRepository.save(order);
    }

    /**
     * Cancels an order, returning stock to its original fulfillment location.
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderId));

        Item item = order.getItem();
        Location fulfillmentLocation = order.getFulfillmentLocation(); // Get the location where the order was fulfilled from

        if (fulfillmentLocation == null) {
            throw new IllegalStateException("Order record is missing its fulfillment location. Cannot restock item.");
        }

        // Find the stock record for the item at its original fulfillment location
        StockUnit stockUnit = stockUnitRepository.findByItemAndLocation(item, fulfillmentLocation)
                .orElseThrow(() -> new IllegalStateException("Stock record not found for item: " + item.getName() + " at " + fulfillmentLocation.getName()));

        // Return the ordered quantity back to the stock
        stockUnit.setQuantity(stockUnit.getQuantity() + order.getQuantity());
        stockUnitRepository.save(stockUnit);

        // Now, delete the order from the database
        orderRepository.deleteById(orderId);
    }
}