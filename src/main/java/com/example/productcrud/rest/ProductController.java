package com.example.productcrud.rest;

import com.example.productcrud.exception.NotFoundException;
import com.example.productcrud.model.Product;
import com.example.productcrud.service.ProductService;
import com.example.productcrud.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Resource<Product>> saveProduct(@RequestBody Product product, HttpServletRequest request) {

        log.info("saveProduct method was called");

        Product savedProduct = productService.save(product);

        Resource<Product> productResource = new Resource<>(savedProduct);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getProductById(savedProduct.getId()));
        productResource.add(linkTo.withRel("product/{id}"));

        log.info("product saved with id {}",savedProduct.getId());
        return new ResponseEntity<>(productResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        log.info("getProductById method called. id: {}", id);
        return new ResponseEntity<>(productService.findById(id)
                .orElseThrow(() -> new NotFoundException("ID", id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable long id) {

        log.info("updateProduct called. id:{}, product:{}", id, product);
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent())
            return ResponseEntity.notFound().build();

        Product existingProduct = productOptional.get();
        existingProduct.setName(product.getName());
        existingProduct.setDetail(product.getDetail());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDate(product.getDate());
        existingProduct.setCurrency(product.getCurrency());


        productService.save(existingProduct);

        return new ResponseEntity<>(existingProduct, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable long id) {

        log.info("deleteProduct is called. id:{}", id);
        Product product = productService.findById(id)
                .orElseThrow(() -> new NotFoundException("ID", id));

        productService.deleteById(product.getId());
        return new ResponseEntity<>(new Result(0, "OK"), HttpStatus.OK);
    }


}
