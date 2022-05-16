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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.OnlineShop.Repositories.ProductRepo;
import com.app.OnlineShop.models.*;

@CrossOrigin()
@RestController
@RequestMapping("/product")
public class ManagerController{
	
	private byte[] bytes;
	private String filename;

	@Autowired
	ProductRepo productRepo;
	
	@GetMapping("")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = new ArrayList<Product>();
			productRepo.findAll().forEach((product) -> {
				product.setImage(Base64.getDecoder().decode(product.getImage()));
				products.add(product);
			});
			
			if (products.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		try {
			Optional<Product> product = productRepo.findById(id);
			product.get().setImage(Base64.getDecoder().decode(product.get().getImage()));
			if (product.isPresent()) {
				return new ResponseEntity<>(product.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("image/upload")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws Exception{
		filename = file.getOriginalFilename();
		this.bytes = Base64.getEncoder().encode(file.getBytes());
	}
	@PostMapping("add")
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		try {
			product.setImagename(filename);
			product.setImage(this.bytes);
			this.bytes = null;
			Product newproduct = productRepo.save(product);
			return new ResponseEntity<>(newproduct, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		try {
			Optional<Product> productData = productRepo.findById(id);
			if (productData.isPresent()) {
				Product updateproduct = productData.get();
				
				updateproduct.setName(product.getName());
				updateproduct.setDescription(product.getDescription());
				updateproduct.setPrice(product.getPrice());
				updateproduct.setPriceOrigin(product.getPriceOrigin());
				updateproduct.setCategories(product.getCategories());
				updateproduct.setDiscount(product.getDiscount());
				updateproduct.setQuantity(product.getQuantity());
				if (this.bytes != null) {
					updateproduct.setImage(this.bytes);
					this.bytes = null;
				}
				if (product.getPrice() != product.getPriceOrigin()) {
					updateproduct.setSale(false);
				}
				return new ResponseEntity<>(productRepo.save(updateproduct),HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("delete/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
