package com.ra34.projecte2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.AddressRequest;
import com.ra34.projecte2.DTO.CustomerDTO;
import com.ra34.projecte2.Mapper.CustomerMapper;
import com.ra34.projecte2.Model.Address;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Repository.AddressRepository;
import com.ra34.projecte2.Repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
    }

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

    @Transactional
    public CustomerDTO addAddresses(Long customerId, List<AddressRequest> addressRequests) {
        // El customer ha d’existir 
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no trobat"));

        for (AddressRequest dto : addressRequests) {
            Address newAddress = new Address();
            newAddress.setAddress(dto.getAddress());
            newAddress.setCity(dto.getCity());
            newAddress.setPostalCode(dto.getPostalCode());
            newAddress.setCountry(dto.getCountry());
            newAddress.setIsDefault(dto.getIsDefault());

            customer.addAddress(newAddress);
        }

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO getCustomerById(Long id) {
       
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no trobat"));
        return customerMapper.toDTO(customer);
    }


}
