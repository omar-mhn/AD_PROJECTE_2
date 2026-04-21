package com.ra34.projecte2.DTO;

public class UserRequest {

    private String email;
    private String password;

    private CustomerRequest customer;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerRequest getCustomer() { return customer; }

    public void setCustomer(CustomerRequest customer) { this.customer = customer; }
}