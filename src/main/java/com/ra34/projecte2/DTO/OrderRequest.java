package com.ra34.projecte2.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {

    private long customerId;

    private LocalDateTime orderDate;

    private List<OrderItemRequest> orderItems;

    private String invoiceNumber;
    private LocalDateTime issueDate;
    private float taxAmount;

    // Constructor
    
    public OrderRequest(long customerId, LocalDateTime orderDate, List<OrderItemRequest> orderItems, String invoiceNumber, LocalDateTime issueDate, float taxAmount){
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.taxAmount = taxAmount;
    }

    // Getters i setters
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    public float getTaxAmount() {
        return taxAmount;
    }
    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }
}
