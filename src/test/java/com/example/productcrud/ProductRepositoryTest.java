package com.example.productcrud;


import com.example.productcrud.model.Product;
import com.example.productcrud.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCaseSaveProduct() {

        Product product = new Product( "Product1", new BigDecimal("100"), "Detail 1");
        productRepository.save(product);
        Product product2 = productRepository.findByName("Product1");
        assertNotNull(product2);
        assertEquals(product2.getName(), product.getName());
        assertEquals(product2.getDetail(), product.getDetail());
        assertEquals(product2.getPrice(), product.getPrice());
    }

    @Test
    public void testCaseFindProductById() {

        Product product2 = productRepository.findById(1l).get();
        assertNotNull(product2);
        //from data.sql
        assertEquals(product2.getName(), "PRODUCT");
        assertEquals(product2.getDetail(), "PRODUCT DETAIL");
        assertEquals(product2.getPrice(), new BigDecimal("220.00"));
    }

    @Test
    public void testCaseDeleteProduct() {
        Product product = new Product("Product1", new BigDecimal("100"), "Detail 1");
        productRepository.save(product);
        Product product2 = productRepository.findByName("Product1");
        productRepository.delete(product2);
        List<Product> products = productRepository.findAll();

        //we had one from data sql, we added new one, but then removed one of them
        assertEquals(1, products.size());
    }

    @Test
    public void testCaseFindAllProducts() {
        Product product1 = new Product("Product1", new BigDecimal("100"), "Detail 1");
        Product product2 = new Product("Product2", new BigDecimal("200"), "Detail 2");
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        //one row from data.sql
        assertEquals(3, products.size());
    }

    @Test
    public void testCaseFindAllProductsByNativeQuery() {
        Product product1 = new Product("Product1", new BigDecimal("100"), "Detail 1");
        Product product2 = new Product("Product2", new BigDecimal("200"), "Detail 2");
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = productRepository.findAllWithNativeQuery();
        assertNotNull(products);
        //one row from data.sql
        assertEquals(3, products.size());
    }
}
