package com.ims.Inventory.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Double price;

    @OneToMany(mappedBy = "item")
    private List<StockUnit> stockUnits;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Transient
    private Integer initialQuantity;

    @Transient
    private Long locationId; // This field should already be here

    
    // --- ADD THIS GETTER AND SETTER ---
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    // --- END OF NEW METHODS ---


    // --- Existing Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public List<StockUnit> getStockUnits() { return stockUnits; }
    public void setStockUnits(List<StockUnit> stockUnits) { this.stockUnits = stockUnits; }
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public Integer getInitialQuantity() { return initialQuantity; }
    public void setInitialQuantity(Integer initialQuantity) { this.initialQuantity = initialQuantity; }
}