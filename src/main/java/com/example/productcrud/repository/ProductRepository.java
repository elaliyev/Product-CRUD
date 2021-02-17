package com.example.productcrud.repository;

import com.example.productcrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Override
    List<Product> findAll();

    Product findByName(String name);

    @Query(
            value = "SELECT * FROM PRODUCT p",
            nativeQuery = true)
    List<Product> findAllWithNativeQuery();
}
