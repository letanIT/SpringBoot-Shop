package com.app.OnlineShop.controllers;

import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.OnlineShop.Repositories.CartRepo;
import com.app.OnlineShop.Repositories.ProductRepo;
import com.app.OnlineShop.models.Cart;
import com.app.OnlineShop.models.Product;

@CrossOrigin()
@RestController
@RequestMapping(path = "/cart")
public class CartController {
	
	boolean isEqual = false;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartRepo cartRepo;

	@GetMapping("")
	public ResponseEntity<List<Product>> getAllItemCart() {
		try {
			List<Cart> carts = cartRepo.findAll();
			List<Product> productsInCart = new ArrayList<Product>();
			
			carts.forEach((e) -> {
				Optional<Product> product = productRepo.findById(e.getProd_id());
				if (product.isPresent()) {
					product.get().setImage(Base64.getDecoder().decode(product.get().getImage()));
					product.get().setQuantity(e.getQuantity());
					productsInCart.add(product.get());
				}
			});
			
			return new ResponseEntity<>(productsInCart, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/{prod_id}/{quantity}")
	public ResponseEntity<Cart> addItem(@PathVariable("prod_id") long prod_id, @PathVariable("quantity") int quantity) {
		try {
			if (quantity <= 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			} else {
				Cart cart = cartRepo.findByProdId(prod_id);
				if (cart == null) {
					Cart cart1 = new Cart();
					cart1.setProd_id(prod_id);
					cart1.setQuantity(quantity);
					return new ResponseEntity<>(cartRepo.save(cart1), HttpStatus.OK);
				} else {
					Optional<Product> product = productRepo.findById(prod_id);
					if (product.isPresent()) {
						product.get().setQuantity(product.get().getQuantity() - quantity);
					}
					quantity = cart.getQuantity() + quantity;
					cart.setQuantity(quantity);
					
					return new ResponseEntity<>(cartRepo.save(cart), HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/delete/{prod_id}")
	public ResponseEntity<HttpStatus> deleteItemInCart(@PathVariable("prod_id") long prod_id) {
		try {
			cartRepo.deleteByProductId(prod_id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
