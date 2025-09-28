package com.ims.Inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private LocalDateTime orderDate;
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // --- NEW DATABASE FIELDS ---
    @Enumerated(EnumType.STRING)
    private OrderFulfillmentType fulfillmentType;

    @ManyToOne
    @JoinColumn(name = "fulfillment_location_id") // Can be null if not applicable
    private Location fulfillmentLocation;
    // --- END OF NEW DATABASE FIELDS ---


    // --- NEW TRANSIENT FIELD FOR THE FORM ---
    @Transient
    private Long fulfillmentLocationId;
    // --- END OF NEW TRANSIENT FIELD ---


    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    // --- GETTERS AND SETTERS FOR NEW FIELDS ---
    public OrderFulfillmentType getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(OrderFulfillmentType fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

    public Location getFulfillmentLocation() {
        return fulfillmentLocation;
    }

    public void setFulfillmentLocation(Location fulfillmentLocation) {
        this.fulfillmentLocation = fulfillmentLocation;
    }
    
    public Long getFulfillmentLocationId() {
        return fulfillmentLocationId;
    }

    public void setFulfillmentLocationId(Long fulfillmentLocationId) {
        this.fulfillmentLocationId = fulfillmentLocationId;
    }
}