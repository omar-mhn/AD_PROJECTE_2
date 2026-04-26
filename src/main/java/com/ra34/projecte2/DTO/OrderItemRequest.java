package com.ra34.projecte2.DTO;

public class OrderItemRequest {

    private int quantity;
    private double unitPrice;
    private Long productId;

    // Constructor
    public OrderItemRequest(int quantity, float unitPrice, Long productId){
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productId = productId;
    }

    // Getters i setters
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
