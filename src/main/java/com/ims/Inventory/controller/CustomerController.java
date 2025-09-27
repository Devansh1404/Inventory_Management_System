package com.ims.Inventory.controller;

import com.ims.Inventory.model.Customer;
import com.ims.Inventory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Display list of all customers
    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        model.addAttribute("listCustomers", customerService.getAllCustomers());
        return "customers"; // Renders customers.html
    }

    // Show form to add a new customer
    @GetMapping("/customers/new")
    public String showNewCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer_form"; // Renders customer_form.html
    }

    // Save customer to database
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/";
    }

    // Show form to edit a customer
    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customer_form";
    }

    // Delete a customer
    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
