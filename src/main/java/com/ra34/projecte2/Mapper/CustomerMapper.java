package com.ra34.projecte2.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;

@Component
public class CustomerMapper {

     // ── Entitat JPA → Customer DTO ─────────────────────────────────────
    public List<CustomerDTO> toDTOS(List<Customer> customers){
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer c: customers){
            CustomerDTO customer = new CustomerDTO();
            customer.setEmail(c.getUser().getEmail());
            customer.setFirstName(c.getFirstName());
            customer.setLastName(c.getLastName());
            customer.setPhone(c.getPhone());
            customer.setAddresses(c.getAddresses());
            dtos.add(customer);
        }
        return dtos;
    }
}
