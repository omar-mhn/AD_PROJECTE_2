package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ra34.projecte2.Model.Product;


public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product>findByNameContainingAndStatusTrue(String name);
}
