package com.company.springjpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(inheritLocations = false, locations = { "classpath:persistence-context.xml" })
public class OrderServiceXmlTest extends OrderServiceTest {
}
