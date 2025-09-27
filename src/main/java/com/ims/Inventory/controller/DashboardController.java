package com.ims.Inventory.controller;

import com.ims.Inventory.model.Customer;
import com.ims.Inventory.model.Item;
import com.ims.Inventory.model.Order;
import com.ims.Inventory.service.CustomerService;
import com.ims.Inventory.service.ItemService;
import com.ims.Inventory.service.OrderService;
import com.ims.Inventory.service.StockUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private StockUnitService stockUnitService;

    /**
     * Prepares all the necessary data for the main dashboard view.
     */
    @GetMapping("/")
    public String showDashboard(Model model) {
        // Load all the lists for the tables on the dashboard
        model.addAttribute("listItems", itemService.getAllItems());
        model.addAttribute("listCustomers", customerService.getAllCustomers());
        model.addAttribute("listOrders", orderService.getAllOrders());
        model.addAttribute("listStockUnits", stockUnitService.getAllStockUnits());

        // Prepare empty objects for each of the "Add" and "Edit" modals
        // This is crucial to prevent Thymeleaf parsing errors.
        model.addAttribute("newItem", new Item());
        model.addAttribute("newCustomer", new Customer());
        model.addAttribute("newOrder", new Order());
        model.addAttribute("editItem", new Item()); // <-- THIS IS THE FIX

        return "dashboard"; // Renders dashboard.html
    }
}

