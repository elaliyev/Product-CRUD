package com.example.productcrud;


import com.example.productcrud.model.Product;
import com.example.productcrud.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {

        Product product = new Product(44l, "Product1", new BigDecimal("100"), "Detail 1");
        productRepository.save(product);
        Product product2 = productRepository.findById(44l).get();
        assertNotNull(product2);
        assertEquals(product2.getName(), product.getName());
        assertEquals(product2.getDetail(), product.getDetail());
        assertEquals(product2.getPrice(), product.getPrice());
    }

    @Test
    public void testGetProduct() {

        Product product = new Product(44l, "Product1", new BigDecimal("100"), "Detail 1");
        productRepository.save(product);
        Product product2 = productRepository.findById(44l).get();
        assertNotNull(product2);
        assertEquals(product2.getName(), product.getName());
        assertEquals(product2.getDetail(), product.getDetail());
        assertEquals(product2.getPrice(), product.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(44l, "Product1", new BigDecimal("100"), "Detail 1");
        productRepository.save(product);
        productRepository.delete(product);
    }

    @Test
    public void findAllProducts() {
        Product product1 = new Product(44l, "Product1", new BigDecimal("100"), "Detail 1");
        Product product2 = new Product(45l, "Product2", new BigDecimal("200"), "Detail 2");
        productRepository.save(product1);
        productRepository.save(product2);
        assertNotNull(productRepository.findAll());
        //one from data.sql
        assertEquals(3, productRepository.findAll().size());
    }
}
