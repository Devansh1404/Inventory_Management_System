package com.ims.Inventory.controller;

import com.ims.Inventory.service.StockUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StockUnitController {

    @Autowired
    private StockUnitService stockUnitService;

    /**
     * Handles the form submission from the "Update Stock" modal.
     * It takes the ID of the stock unit and the new quantity from the form.
     */
    @PostMapping("/stock/update")
    public String updateStockQuantity(@RequestParam("stockUnitId") Long stockUnitId,
                                      @RequestParam("newQuantity") int newQuantity,
                                      RedirectAttributes redirectAttributes) {
        try {
            stockUnitService.updateStockQuantity(stockUnitId, newQuantity);
            redirectAttributes.addFlashAttribute("successMessage", "Stock quantity updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating stock: " + e.getMessage());
        }
        // After updating, redirect back to the main dashboard
        return "redirect:/";
    }
}

