package com.example.productcrud.service;

import com.example.productcrud.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();
    Optional<Category> findById(long id);
    Category save(Category category);
    void deleteById(long id);
}
