package com.company.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.springjpa.domain.order.Order;

/**
 * Jpa repository for {@link Order}
 * 
 * @author Joris Aper
 * 
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}