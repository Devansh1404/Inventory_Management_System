package com.ims.Inventory.repository;

import com.ims.Inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Add this import
import org.springframework.stereotype.Repository;

import java.util.List; // Add this import

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add this new method
    @Query("SELECT o FROM Order o JOIN FETCH o.customer JOIN FETCH o.item")
    List<Order> findAllWithDetails();
}