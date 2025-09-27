package com.ims.Inventory.service;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.Order;
import com.ims.Inventory.model.StockUnit;
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

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Saves a new order. This is a transactional operation that involves:
     * 1. Finding the stock record for the ordered item.
     * 2. Validating if there is enough stock.
     * 3. Decreasing the stock quantity.
     * 4. Calculating the total price.
     * 5. Saving the finalized order.
     */
    @Override
    @Transactional
    public Order saveOrder(Order order) throws InsufficientStockException {
        Item item = order.getItem();
        StockUnit stockUnit = stockUnitRepository.findByItem(item)
                .orElseThrow(() -> new IllegalStateException("Stock record not found for item: " + item.getName()));

        // Check if there is enough quantity in stock
        if (stockUnit.getQuantity() < order.getQuantity()) {
            throw new InsufficientStockException("Not enough stock for " + item.getName() + ". Only " + stockUnit.getQuantity() + " available.");
        }

        // Decrease the stock quantity
        stockUnit.setQuantity(stockUnit.getQuantity() - order.getQuantity());
        stockUnitRepository.save(stockUnit);

        // Finalize the order details before saving
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(item.getPrice() * order.getQuantity());
        return orderRepository.save(order);
    }

    /**
     * Cancels an order. This is a transactional operation that involves:
     * 1. Finding the order to be canceled.
     * 2. Finding the corresponding stock record.
     * 3. Increasing the stock quantity (restocking the item).
     * 4. Deleting the order record.
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderId));

        Item item = order.getItem();
        StockUnit stockUnit = stockUnitRepository.findByItem(item)
                .orElseThrow(() -> new IllegalStateException("Stock record not found for item: " + item.getName()));

        // Return the ordered quantity back to the stock
        stockUnit.setQuantity(stockUnit.getQuantity() + order.getQuantity());
        stockUnitRepository.save(stockUnit);

        // Now, delete the order from the database
        orderRepository.deleteById(orderId);
    }
}

