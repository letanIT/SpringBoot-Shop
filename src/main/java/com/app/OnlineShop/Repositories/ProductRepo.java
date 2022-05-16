package com.app.OnlineShop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.OnlineShop.models.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
}
