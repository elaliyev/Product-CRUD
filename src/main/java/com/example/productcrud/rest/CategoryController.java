package com.example.productcrud.rest;

import com.example.productcrud.exception.NotFoundException;
import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.service.CategoryService;
import com.example.productcrud.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<Resource<Category>> saveCategory(@RequestBody Category category, HttpServletRequest request) {

        log.info("saveCategory method was called");

        Category savedCategory = categoryService.save(category);

        Resource<Category> categoryResource = new Resource<>(savedCategory);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCategoryById(savedCategory.getId()));
        categoryResource.add(linkTo.withRel("category/{id}"));

        log.info("category saved with id {}",savedCategory.getId());
        return new ResponseEntity<>(categoryResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        log.info("getCategoryById method called. id: {}", id);
        return new ResponseEntity<>(categoryService.findById(id)
                .orElseThrow(() -> new NotFoundException("ID", id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable long id) {

        log.info("updateCategory called. id:{}, category:{}", id, category);
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (!categoryOptional.isPresent())
            return ResponseEntity.notFound().build();

        Category existingCategory = categoryOptional.get();
        existingCategory.setCategoryName(category.getCategoryName());

        categoryService.save(existingCategory);

        return new ResponseEntity<>(existingCategory, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable long id) {

        log.info("deleteCategory is called. id:{}", id);
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new NotFoundException("ID", id));

        categoryService.deleteById(category.getId());
        return new ResponseEntity<>(new Result(0, "OK"), HttpStatus.OK);
    }

}
