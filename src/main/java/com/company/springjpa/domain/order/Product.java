package com.company.springjpa.domain.order;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.company.springjpa.domain.AbstractEntity;

@Entity
public class Product extends AbstractEntity<Long, Long>{

    private String name;

    private BigDecimal price;

    public Product() {
    }

    public Product(String name, String price) {
        this.name = name;
        this.price = new BigDecimal(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
