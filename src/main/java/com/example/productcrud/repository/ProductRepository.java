package com.example.productcrud.repository;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    List<Product> findProductsByCategory(Category category);

    @Override
    List<Product> findAll();
}
