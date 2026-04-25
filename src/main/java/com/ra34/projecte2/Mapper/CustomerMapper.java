package com.ra34.projecte2.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.AddressDTO;
import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;

@Component
public class CustomerMapper {

     // ── Entitat JPA → Customer DTO ─────────────────────────────────────
    public List<CustomerDTO> toDTOS(List<Customer> customers) {
    List<CustomerDTO> dtos = new ArrayList<>();
    
    for (Customer c : customers) {
        CustomerDTO dto = new CustomerDTO();
        
        // 1. Mapping des champs simples
        dto.setEmail(c.getUser().getEmail());
        dto.setFirstName(c.getFirstName());
        dto.setLastName(c.getLastName());
        dto.setPhone(c.getPhone());

        
        List<AddressDTO> addressDtos = new ArrayList<>();
        if (c.getAddresses() != null) {
            for (Address a : c.getAddresses()) {
                
                AddressDTO adto = new AddressDTO(
                    a.getId(), 
                    a.getAddress(), 
                    a.getCity(), 
                    a.getPostalCode(), 
                    a.getCountry(), 
                    a.getIsDefault()
                );
                addressDtos.add(adto);
            }
        }
    
        dto.setAddresses(addressDtos); 
        
        dtos.add(dto);
    }
    return dtos;
}

    public CustomerDTO toDTO(Customer c) {
        if (c == null) return null;

        CustomerDTO dto = new CustomerDTO();
        
        if (c.getUser() != null) {
            dto.setEmail(c.getUser().getEmail());
        }
        
        dto.setFirstName(c.getFirstName());
        dto.setLastName(c.getLastName());
        dto.setPhone(c.getPhone());

        
        List<AddressDTO> addressDTOs = new ArrayList<>();
        if (c.getAddresses() != null) {
            for (Address a : c.getAddresses()) {
                
                AddressDTO aDto = new AddressDTO(
                    a.getId(), a.getAddress(), a.getCity(), 
                    a.getPostalCode(), a.getCountry(), a.getIsDefault()
                );
                addressDTOs.add(aDto);
            }
        }
        dto.setAddresses(addressDTOs);

        return dto;
    }
}
