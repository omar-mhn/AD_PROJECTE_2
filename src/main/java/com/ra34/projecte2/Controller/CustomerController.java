package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.Service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {   

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.findAll();
        
        return ResponseEntity.ok(customers);
    } 
    
    @DeleteMapping("/customers/{id}/addresses")
    public ResponseEntity<String> deleteAllAddresses(@PathVariable Long id){        
        customerService.deleteAllAdress(id);        
        return ResponseEntity.status(HttpStatus.OK).body("Totes les adreces del customer " + id + " han estat esborrades correctament");
    }   


}
