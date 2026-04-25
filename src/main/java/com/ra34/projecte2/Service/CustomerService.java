package com.ra34.projecte2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.Mapper.CustomerMapper;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Repository.AddressRepository;
import com.ra34.projecte2.Repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    
    @Autowired 
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional  //pq borrem diverses adreces
    public void deleteAllAdress(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            addressRepository.deleteByCustomerId(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No s'ha trobat el customer amb id: " + id);
        }
    }
    
    public List<CustomerDTO> findAll(){        
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No s'han trobat customers");
        }

        List<CustomerDTO> customers = customerMapper.toDTOS(customerList);
        return customers;
    } 


}
