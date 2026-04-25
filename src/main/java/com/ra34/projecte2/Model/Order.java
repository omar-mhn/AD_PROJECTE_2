package com.ra34.projecte2.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name  ="total_amount",nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status",nullable = false)
    private orderStatus orderStatus;

    private boolean status;

    @CreationTimestamp
    @Column(name = "data_created",updatable = false)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Invoice invoice;

    public Order(){}

    

    public Order(Long id, LocalDateTime orderDate, double totalAmount, com.ra34.projecte2.Model.orderStatus orderStatus,
            boolean status, LocalDateTime dataCreated, LocalDateTime dataUpdated, Customer customer) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
        this.customer = customer;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public orderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(orderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(LocalDateTime dataCreated) {
        this.dataCreated = dataCreated;
    }

    public LocalDateTime getDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(LocalDateTime dataUpdated) {
        this.dataUpdated = dataUpdated;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        if(invoice != null){
            invoice.setOrder(this);
        }
    }

    


    

}
