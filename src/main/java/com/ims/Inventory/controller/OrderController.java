package com.ims.Inventory.controller;

import com.ims.Inventory.model.Order;
import com.ims.Inventory.service.InsufficientStockException;
import com.ims.Inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Handles the form submission from the "Add New Order" modal.
     * It attempts to save the order and handles potential stock issues.
     */
    @PostMapping("/orders/save")
    public String saveOrder(@ModelAttribute("newOrder") Order order, RedirectAttributes redirectAttributes) {
        try {
            orderService.saveOrder(order);
            redirectAttributes.addFlashAttribute("successMessage", "Order created successfully!");
        } catch (InsufficientStockException e) {
            // If there's not enough stock, send a specific error message back to the dashboard
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating order: " + e.getMessage());
        }
        return "redirect:/";
    }

    /**
     * Handles the request to cancel an order by its ID.
     * This will also restock the items from the canceled order.
     */
    @GetMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("successMessage", "Order has been canceled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error canceling order: " + e.getMessage());
        }
        return "redirect:/";
    }
}

