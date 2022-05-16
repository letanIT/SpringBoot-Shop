package com.app.OnlineShop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.OnlineShop.Repositories.OrderRepo;
import com.app.OnlineShop.models.Order;

@CrossOrigin()
@RestController
@RequestMapping(path = "/order")
public class OrderController {

	@Autowired
	OrderRepo orderrepo;
	
	@GetMapping("")
	public ResponseEntity<List<Order>> getAllOrder() {
		try {
			List<Order> orders = orderrepo.findAll();
			if (orders.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			};
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/checkout")
	public ResponseEntity<?> checkout(@RequestBody Order order) {
		Order neworder = orderrepo.save(order);
		return new ResponseEntity<>(neworder, HttpStatus.CREATED);
	}
	@PutMapping("/{id}/{status}")
	public ResponseEntity<?> changeStatus(@PathVariable("id") long id, @PathVariable("status") String status) {
		try {
			Optional<Order> old_order = orderrepo.findById(id);
			
			if (!old_order.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			old_order.get().setStatus(status);
			return new ResponseEntity<>(old_order.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {
		try {
			orderrepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
