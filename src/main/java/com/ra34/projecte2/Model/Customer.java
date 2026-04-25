package com.ra34.projecte2.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;

    private boolean status;
    
    @CreationTimestamp
    @Column(name = "data_created",updatable = false)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;

    @OneToOne(fetch = FetchType.LAZY) //no carrega l'user fins q es cridi getUser()
    @JoinColumn(name = "user_Id", unique = true)
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // Constructors
    public Customer(){
    }

    public Customer(String firstName, String lastName, String phone, Boolean status, LocalDateTime dataCreated, LocalDateTime dataUpdated){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
    }

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addAddress(Address address){
        this.addresses.add(address);  // 1) actualitza la llista del costat invers (Customer)
        address.setCustomer(this);  // 2) actualitza el camp del costat PROPIETARI (Address)
    }

    public void removeAddress(Address address){
        this.addresses.remove(address);  
        address.setCustomer(null);  
    }

    public void addOrder (Order order){
        this.orders.add(order); 
        order.setCustomer(this);
    }

    public void removeOrder (Order order){
        this.orders.remove(order);
        order.setCustomer(null); // posa customer_id = NULL
    }
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }    

}
