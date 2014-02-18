package com.company.springjpa;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = { "com.company.springjpa.repository" })
@ComponentScan(basePackages = { "com.company.springjpa.service.impl" })
@PropertySource(value = { "classpath:hibernate.properties" })
@EnableTransactionManagement
@Configuration
public class TestRepositoryConfig {

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource h2ServerDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public DataSource inMemoryDataSource() {
		return new EmbeddedDatabaseBuilder()//
				.setType(EmbeddedDatabaseType.H2)//
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		// entityManagerFactory.setDataSource(h2ServerDataSource());
		entityManagerFactory.setDataSource(inMemoryDataSource());
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPersistenceUnitName("ApplicationEntityManager");
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		jpaProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		jpaProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}
