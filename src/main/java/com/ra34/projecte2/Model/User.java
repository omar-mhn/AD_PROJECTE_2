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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity // representa la taula a bbdd "user" i q és una entitat gestionada per JPA
@Table(name = "users")
public class User {

    @Id  //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //La BD genera el valor automàticament (recomanat per MySQl i PostgreSQL)
    private Long id;

    @Column(nullable = false, unique = true) //personalitza el comportament d’un camp
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean status;

    @CreationTimestamp
    @Column(name = "data_created", updatable = false)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToMany
    @JoinTable(           //per indicar la taula intermediaria
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    //Constructors
    public User() {
    }

    public User(String email, String password, boolean status, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
    }

    // // Getters i Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    // Setter especial per 1to1: manté els dos costats sincronitzats
    public void setCustomer(Customer customer) {
        this.customer = customer;       // 1) actualitza el costat invers (User)
        if (customer != null) {
            customer.setUser(this);       // 2) actualitza el costat PROPIETARI Customer)
        }
    }

}
