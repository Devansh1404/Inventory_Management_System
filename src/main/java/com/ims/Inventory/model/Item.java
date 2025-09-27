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

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private StockUnit stockUnit;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Order> orders;

    // This field is not saved to the database. It's used only for the form.
    @Transient
    private Integer initialQuantity; // <-- CHANGED from int to Integer

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public StockUnit getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(StockUnit stockUnit) {
        this.stockUnit = stockUnit;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getInitialQuantity() { // <-- CHANGED
        return initialQuantity;
    }

    public void setInitialQuantity(Integer initialQuantity) { // <-- CHANGED
        this.initialQuantity = initialQuantity;
    }
}

