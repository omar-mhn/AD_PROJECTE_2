package com.ra34.projecte2.Model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity// entitat JPA =>mapeig de la base de dades a la classe
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private int stock;
    
    @Column// Tipo primitivo (double) ya que el enunciado especifica "not null"
    private double price;
    
    @Column// Tipo objeto (Double) -> clase envoltorio (wrapper), admite null. El enunciado indica que tiene q admitir valores nulos
    private Double rating;

    @Enumerated(EnumType.STRING) // Esto hace que la base de datos sea mucho más legible y evita errores
    @Column(name = "product_condition")
    private Condition condition;

    @Column(nullable = false)
    private boolean status = true;

    @Column(name = "data_created")
    private Timestamp dataCreated;

    @Column(name = "data_updated")
    private Timestamp dataUpdated;

    @OneToMany(mappedBy ="product", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String name, String description, int stock, double price, Double rating, Condition condition, boolean status, Timestamp dataCreated, Timestamp dataUpdated) {
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
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", stock=" + stock + ", price=" + price + ", rating=" + rating + ", condition=" + condition + ", status=" + status + ", dataCreated=" + dataCreated + ", dataUpdated=" + dataUpdated + "]";
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setProduct(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setProduct(null);
    }
    
}
