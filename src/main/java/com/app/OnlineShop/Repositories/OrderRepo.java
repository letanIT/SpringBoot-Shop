package com.app.OnlineShop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.OnlineShop.models.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
