package com.ra34.projecte2.DTO;

public class UserDTO {

    private Long id;
    private String email; 
    
    private CustomerDTO customer;

    // Constructor sense customer (per evitar recursivitat)
    public UserDTO(Long id, String email){
        this.id = id;
        this.email = email;
    }

    // Constructor amb customer
    public UserDTO(Long id, String email, CustomerDTO customer){
        this.id = id;
        this.email = email;
        this.customer = customer;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}