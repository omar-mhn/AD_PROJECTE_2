package com.ra34.projecte2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ra34.projecte2.Model.Product;


public interface ProductRepository extends JpaRepository<Product,Long>{
    
}
