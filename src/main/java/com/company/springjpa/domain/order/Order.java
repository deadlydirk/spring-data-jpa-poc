package com.company.springjpa.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.company.springjpa.domain.AbstractEntity;

@Entity
@Table(name = "product_order")
public class Order extends AbstractEntity<Long, Long> {

    private String customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();

    public Order() {
    }
    
    public Order(String customer) {
        this.customer = customer;
    }
    
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addLine(Product product, int quantity) {
        orderLines.add(new OrderLine(product, quantity));
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

}
