package com.ims.Inventory.service;

import com.ims.Inventory.model.Order;
import java.util.List;

public interface OrderService {
    
    /**
     * Retrieves a list of all orders from the database.
     * @return a list of all Order objects.
     */
    List<Order> getAllOrders();

    /**
     * Saves a new order, automatically updating stock levels.
     * @param order The new order object to be saved.
     * @return The saved order with its generated ID and order date.
     * @throws InsufficientStockException if the ordered quantity is greater than the available stock.
     */
    Order saveOrder(Order order) throws InsufficientStockException;

    /**
     * Cancels an existing order. This involves deleting the order record
     * and returning the ordered quantity back to the item's stock.
     * @param orderId The ID of the order to be cancelled.
     */
    void cancelOrder(Long orderId);
}

