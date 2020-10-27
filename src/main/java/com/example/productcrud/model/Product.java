package com.example.productcrud.model;

import com.example.productcrud.utils.Currency;
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

    @Column(name="NAME")
    private String name;

    @Column(name="PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name="CREATION_DATE")
    private LocalDate date;

    @Column(name="DETAIL")
    private String detail;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID", nullable = false)
    private Category category;

}
