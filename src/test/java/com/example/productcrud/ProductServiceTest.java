package com.example.productcrud;

import com.example.productcrud.model.Product;
import com.example.productcrud.repository.ProductRepository;
import com.example.productcrud.service.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllProducts(){

        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product(101l, "Product1", new BigDecimal("100"), "Detail 1"));
        productList.add(new Product(102l, "Product2", new BigDecimal("200"), "Detail 2"));
        productList.add(new Product(103l, "Product3", new BigDecimal("300"), "Detail 3"));

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();
        assertEquals(3, result.size());
    }
    @Test
    public void testGetProductById(){
        Product product = new Product(50l, "Product1", new BigDecimal("100"), "Detail 1");
        when(productRepository.findById(50l).get()).thenReturn(product);
        Product result = productService.findById(50).get();
        assertEquals(50l, result.getId().longValue());
        assertEquals("Detail 1", result.getDetail());
        assertEquals(new BigDecimal("100"), result.getPrice());
        assertEquals("Product1", result.getName());
    }
    @Test
    public void saveProduct(){
        Product product = new Product(500l, "Product1", new BigDecimal("100"), "Detail 1");
        when(productRepository.save(product)).thenReturn(product);
        Product result = productService.save(product);
        assertEquals(500l, result.getId().longValue());
        assertEquals("Detail 1", result.getDetail());
        assertEquals(new BigDecimal("100"), result.getPrice());
        assertEquals("Product1", result.getName());
    }
    @Test
    public void removeCoupon(){
        Product product = new Product(1l, "Product1", new BigDecimal("100"), "Detail 1");
        productService.deleteById(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
