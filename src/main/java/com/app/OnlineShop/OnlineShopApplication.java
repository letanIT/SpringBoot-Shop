package com.app.OnlineShop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.app.OnlineShop.models.Product;

@SpringBootApplication()
public class OnlineShopApplication implements CommandLineRunner {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String query = "SELECT * FROM PRODUCT";
		List<Product> products = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Product.class));
	}

}
