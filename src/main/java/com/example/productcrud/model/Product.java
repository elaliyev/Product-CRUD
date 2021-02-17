package com.example.productcrud.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", unique = true)
    private String name;

    @Column(name="PRICE")
    private BigDecimal price;

    @Column(name="DETAIL")
    private String detail;

    public Product(String name, BigDecimal price, String detail) {
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
}
