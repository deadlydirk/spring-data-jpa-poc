package com.company.springjpa.service;

import com.company.springjpa.domain.order.Order;

public interface OrderService {

    Order save(Order order);
    
    Order findByIdAndUpdateName(Long id);

	Order findOne(Long id);

	void delete(Order order);

	Order findByCustomer(final String customer);
}
