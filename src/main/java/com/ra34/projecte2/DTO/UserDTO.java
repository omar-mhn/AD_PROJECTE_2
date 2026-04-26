package com.ra34.projecte2.DTO;

import java.util.List;

public class UserDTO {

    private Long id;
    private String email; 
    private CustomerDTO customer;
    private List<RoleDTO> roles;

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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
<<<<<<< HEAD
=======

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    
>>>>>>> e5aedd86f754e6e33b102f4f8b7971d208a63e76
}