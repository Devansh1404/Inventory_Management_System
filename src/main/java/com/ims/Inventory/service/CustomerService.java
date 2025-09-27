package com.ims.Inventory.service;

import com.ims.Inventory.model.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Interface for customer management business logic.
 * It defines the contract for what a Customer service must do.
 */
public interface CustomerService {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long id);
}