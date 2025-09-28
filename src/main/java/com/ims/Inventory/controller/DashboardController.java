package com.ims.Inventory.controller;

import com.ims.Inventory.model.Customer;
import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.LocationType; // <-- Add this import
import com.ims.Inventory.model.Order;
import com.ims.Inventory.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired private ItemService itemService;
    @Autowired private CustomerService customerService;
    @Autowired private OrderService orderService;
    @Autowired private StockUnitService stockUnitService;
    @Autowired private LocationService locationService;

    @GetMapping("/")
    public String showDashboard(Model model) {
        // Load existing lists for the tables
        model.addAttribute("listItems", itemService.getAllItems());
        model.addAttribute("listCustomers", customerService.getAllCustomers());
        model.addAttribute("listOrders", orderService.getAllOrders());
        model.addAttribute("listStockUnits", stockUnitService.getAllStockUnits());
        model.addAttribute("listLocations", locationService.getAllLocations());

        // --- THIS IS THE NEW LINE TO ADD ---
        // This provides a list of only retail stores for the order form
        model.addAttribute("listStores", locationService.getLocationsByType(LocationType.RETAIL_STORE));

        // Prepare empty objects for the modals
        model.addAttribute("newItem", new Item());
        model.addAttribute("newCustomer", new Customer());
        model.addAttribute("newOrder", new Order());
        model.addAttribute("editItem", new Item());

        return "dashboard";
    }
}