package com.company.springjpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.company.springjpa.domain.order.Order;
import com.company.springjpa.domain.order.Product;
import com.company.springjpa.repository.OrderRepository;
import com.company.springjpa.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestRepositoryConfig.class })
public class OrderServiceTest implements ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

	@Autowired
	private OrderService orderService;

	@PersistenceContext
	private EntityManager entityManager;

	private ApplicationContext applicationContext;

	@Test
	public void testBeans() {
		assertNotNull(applicationContext.getBean(OrderRepository.class));
		assertNotNull(applicationContext.getBean("h2ServerDataSource", DataSource.class));
		assertNotNull(applicationContext.getBean("inMemoryDataSource", DataSource.class));
		assertNotNull(applicationContext.getBean(PlatformTransactionManager.class));
		assertNotNull(applicationContext.getBean(LocalContainerEntityManagerFactoryBean.class));
	}

	@Transactional
	@Test
	public void testTransactionalPersistOrderWithItems() {

		Product chestnut = new Product("Chestnut", "2.50");
		entityManager.persist(chestnut);
		Product hazelnut = new Product("Hazelnut", "5.59");
		entityManager.persist(hazelnut);

		Order order = new Order("TEST");
		order.addLine(chestnut, 20);
		order.addLine(hazelnut, 150);

		orderService.save(order);
		entityManager.flush();
		entityManager.clear();

		Order persistent = orderService.findOne(order.getId());
		assertNotNull(persistent.getId());
		assertEquals(order.getCustomer(), persistent.getCustomer());
		assertFalse(order.getOrderLines().isEmpty());
		assertEquals(2, order.getOrderLines().size());
	}

	@Test
	public void testNonTransactionalSaveAndDelete() {
		Order order = new Order("TEST");
		Order savedOrder = orderService.save(order);
		orderService.delete(savedOrder);
	}

	@Test
	public void testNonTransactionalSaveAndUpdate() throws InterruptedException {
		Order order = new Order("TEST");
		Order orderSaved = orderService.save(order);
		assertSame(order, orderSaved);
		LOGGER.debug("last updated: {}", orderSaved.getLastModified());
		orderSaved.setCustomer("NEW");
		Order orderUpdated = orderService.save(orderSaved);
		assertNotSame(orderSaved, orderUpdated);
		LOGGER.debug("last updated: {}", orderUpdated.getLastModified());
		Order orderFound = orderService.findOne(orderUpdated.getId());
		assertEquals(orderUpdated.getCustomer(), orderFound.getCustomer());
		orderService.delete(orderFound);
	}

	@Test
	public void testNonTransactionalSaveAndFind() {
		Order order = new Order("TEST");
		order = orderService.save(order);
		assertEquals(new Long(0), order.getVersion());
		order.setCustomer("NEW");
		order = orderService.save(order);
		Order persistent = orderService.findByIdAndUpdateName(order.getId());
		assertEquals(new Long(1), persistent.getVersion());
		orderService.delete(persistent);
	}

	@Test
	public void testFindBySpecification() {
		final String customer = "CUSTOMER";
		Order order = new Order(customer);
		orderService.save(order);
		Order persistent = orderService.findByCustomer(customer);
		assertEquals(order.getCustomer(), persistent.getCustomer());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
