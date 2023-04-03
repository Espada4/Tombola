package com.prefbm.tombola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class TombolaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TombolaApplication.class, args);
	}

	@Bean
	@DependsOn("dbInitializer")
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		ds.setUrl("jdbc:hsqldb:hsql://localhost:9001/tombola");
		ds.setUsername("sa");
		ds.setPassword("");
		return ds;
	}

}
