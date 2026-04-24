package com.ra34.projecte2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ra34.projecte2.Model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    void deleteByCustomerId(Long customerId);
}
