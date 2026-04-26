package com.ra34.projecte2.DTO;

import com.ra34.projecte2.Model.Condition;

public class ProductResponseDTO {
    
    private String name;
    private String description;
    private int stock;
    private double price;
    private Double rating;
    private Condition condition;
    private Long id;

    public ProductResponseDTO(){}
    
    public ProductResponseDTO(String name, String description, int stock, double price, Double rating, Condition condition) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.rating = rating;
        this.condition = condition;
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

    public void setId(Long id) {
        this.id = id;
    }    
}
