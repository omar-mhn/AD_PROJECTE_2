package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.Condition;

public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product>findByNameContainingAndStatusTrue(String name);
    List<Product> findByStatusTrueOrderByPriceAsc();
    List<Product> findByStatusTrueOrderByPriceDesc();

    List<Product>findByConditionAndStatusTrue(Condition condition);
    List<Product> findByStatusTrueOrderByRatingAsc();
    List<Product> findByStatusTrueOrderByRatingDesc();
    
    @Query("select p from Product p WHERE p.price BETWEEN :priceMin AND :priceMax " + "AND p.name LIKE %:prefix% AND p.status = true")
    List<Product> findWithFiltresJPQL(@Param("priceMax") Double priceMax, @Param("priceMin")Double priceMin, @Param("prefix") String prefix,Pageable pageable);// Pagable Objeto que gestiona el LIMIT y el ORDER (ASC/DESC)
    //// Consulta para obtener el top 5 basado en el cálculo (Rating / Price)
    @Query("SELECT p FROM Product p WHERE p.status = true AND p.price > 0 " + "ORDER BY (p.rating / p.price) DESC")
    List<Product> findTopQualityPrice(Pageable pageable);

}