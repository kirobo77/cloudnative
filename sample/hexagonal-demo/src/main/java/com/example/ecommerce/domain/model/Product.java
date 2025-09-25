// src/main/java/com/example/ecommerce/domain/model/Product.java
package com.example.ecommerce.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(precision = 18, scale = 2, nullable=false)
    private BigDecimal price;

    public Product() {}
    public Product(String name, BigDecimal price){
        this.name = name;
        this.price = price;
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public BigDecimal getPrice(){ return price; }
    public void setName(String name){ this.name = name; }
    public void setPrice(BigDecimal price){ this.price = price; }
}
