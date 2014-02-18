package com.company.springjpa.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.springjpa.domain.order.Order;
import com.company.springjpa.repository.OrderRepository;
import com.company.springjpa.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Transactional(readOnly = false)
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order findByIdAndUpdateName(Long id) {
        Order order = orderRepository.findOne(id);
        order.setCustomer(order.getCustomer() + "'");
        return order;
    }

    @Transactional(readOnly = true)
	@Override
	public Order findOne(Long id) {
		return orderRepository.findOne(id);
	}

    @Transactional(readOnly = false)
	@Override
	public void delete(Order order) {
		orderRepository.delete(order.getId());
	}

	@Transactional(readOnly = true)
	@Override
	public Order findByCustomer(final String customer) {
		Specification<Order> byCustomerSpec = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customer"), customer);
            }
        };
		return orderRepository.findOne(byCustomerSpec);
	}

}
