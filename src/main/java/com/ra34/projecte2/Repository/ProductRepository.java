package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.Condition;

public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product>findByNameContainingAndStatusTrue(String name);
    List<Product> findByStatusTrueOrderByPriceAsc();
    List<Product> findByStatusTrueOrderByPriceDesc();

    List<Product>findByConditionAndStatusTrue(Condition condition);
    List<Product> findByStatusTrueOrderByRatingAsc();
    List<Product> findByStatusTrueOrderByRatingDesc();



}