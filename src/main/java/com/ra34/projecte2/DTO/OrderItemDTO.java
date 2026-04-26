package com.ra34.projecte2.DTO;

public class OrderItemDTO {
    
    private int quantity;
    private double unitPrice;
    private ProductResponseDTO product;
    
    public OrderItemDTO() {}

    public OrderItemDTO(int quantity, double unitPrice, ProductResponseDTO product) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    // OrderItem sense product per evitar recursivitat
    public OrderItemDTO(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductResponseDTO getProduct() {
        return product;
    }
    public void setProduct(ProductResponseDTO product) {
        this.product = product;
    }    
}
