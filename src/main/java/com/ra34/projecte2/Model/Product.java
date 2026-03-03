package com.ra34.projecte2.Model;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private int stock;
    // Tipo primitivo (double) ya que el enunciado especifica "not null"
    @Column
    private double price;
    // Tipo objeto (Double) porque el enunciado indica que admite valores nulos
    @Column
    private Double rating;

    @Enumerated(EnumType.STRING) // Esto hace que la base de datos sea mucho más legible y evita errores
    private Condition condition;

    @Column(nullable = false)
    private boolean status = true;

    @Column
    private Timestamp dataCreated;

    @Column 
    private Timestamp dataUpdated;

    public Product() {
    }

    public Product(Long id, String name, String description, int stock, double price, Double rating,
            Condition condition, boolean status, Timestamp dataCreated, Timestamp dataUpdated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.condition = condition;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Timestamp dataCreated) {
        this.dataCreated = dataCreated;
    }

    public Timestamp getDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(Timestamp dataUpdated) {
        this.dataUpdated = dataUpdated;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", stock=" + stock + ", price="
                + price + ", rating=" + rating + ", condition=" + condition + ", status=" + status + ", dataCreated="
                + dataCreated + ", dataUpdated=" + dataUpdated + "]";
    }

    
}
