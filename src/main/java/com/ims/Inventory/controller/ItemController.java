package com.ims.Inventory.controller;

import com.ims.Inventory.model.Item;
import com.ims.Inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Handles the form submission from the "Add New Item" modal.
     */
    @PostMapping("/items/save")
    public String saveItem(@ModelAttribute("newItem") Item item, RedirectAttributes redirectAttributes) {
        try {
            itemService.saveItem(item);
            redirectAttributes.addFlashAttribute("successMessage", "Item '" + item.getName() + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding item: " + e.getMessage());
        }
        return "redirect:/";
    }

    /**
     * Handles the form submission from the "Edit Item" modal to update an existing item.
     */
    @PostMapping("/items/update")
    public String updateItem(@ModelAttribute("editItem") Item item, RedirectAttributes redirectAttributes) {
        try {
            itemService.updateItem(item);
            redirectAttributes.addFlashAttribute("successMessage", "Item '" + item.getName() + "' updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating item: " + e.getMessage());
        }
        return "redirect:/";
    }

    /**
     * Handles the request to delete an item by its ID.
     */
    @GetMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            itemService.deleteItem(id);
            redirectAttributes.addFlashAttribute("successMessage", "Item deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting item: " + e.getMessage());
        }
        return "redirect:/";
    }
}

