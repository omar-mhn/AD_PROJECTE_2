package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ra34.projecte2.Model.Condition;
import com.ra34.projecte2.Model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product>findByNameContainingAndStatusTrue(String name);
    List<Product> findByStatusTrueOrderByPriceAsc();
    List<Product> findByStatusTrueOrderByPriceDesc();
    List<Product>findByConditionAndStatusTrue(Condition condition);
    List<Product> findByStatusTrueOrderByRatingAsc();
    List<Product> findByStatusTrueOrderByRatingDesc(); 
    
    // Cerca per rang de preu, prefix i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price BETWEEN :min AND :max AND p.name LIKE %:prefix%")
    List<Product> findByPriceRangeAndPrefix(@Param("min") Double min, @Param("max") Double max, @Param("prefix") String prefix);

    // Cerca per rang de rating, prefix i que el camp status sigui true
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating BETWEEN :min AND :max AND p.name LIKE %:prefix%")
    List<Product> findByRatingRangeAndPrefix(@Param("min") Double min, @Param("max") Double max, @Param("prefix") String prefix);

    // Cerca per preu mínim i que el camp status sigui true 
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price >= :min")
    List<Product> findByStatusTrueMinPrice(@Param("min") Double min);
    
    // Cerca per rating mínim i que el camp status sigui true 
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.rating >= :min")
    List<Product> findByStatusTrueMinRating(@Param("min") Double min);
    
    // Consulta para obtener el top 5 basado en el cálculo (Rating / Price)
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price > 0 " + "ORDER BY (p.rating / p.price) DESC")
    List<Product> findTopQualityPrice(Pageable pageable);

    // Consulta top 10 prodcutes nous amb major rating
    @Query("SELECT p FROM Product p WHERE p.condition = :cond AND p.status = true ORDER BY p.dataCreated DESC, p.rating DESC")
    List<Product> findBestProductsByCondition(@Param("cond") Condition condition, Pageable pageable);

    // Cerca per lots de 5 productes-files 
    Page<Product> findByStatusTrue(Pageable pageable);

}