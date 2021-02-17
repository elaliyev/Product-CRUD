package com.example.productcrud.service;

import com.example.productcrud.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(long id);
    Product save(Product product);
    void deleteById(long id);
}
