package com.ra34.projecte2.DTO;

import java.util.List;

public class CustomerDTO {

    private Long Id;
    private String firstName;
    private String lastName;
    private String phone;

    private String email;
    private List<AddressDTO> addresses;

    public CustomerDTO(){
    }


    public CustomerDTO(Long id, String firstName, String lastName, String phone){
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

   

    public CustomerDTO(Long id, String firstName, String lastName, String phone, String email,
            List<AddressDTO> addresses) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addresses = addresses;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

  


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public List<AddressDTO> getAddresses() {
        return addresses;
    }


    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

}
