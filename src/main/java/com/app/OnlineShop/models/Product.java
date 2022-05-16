package com.app.OnlineShop.models;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "price")
	private int price;
	@Column(name = "priceorigin")
	private int priceorigin;
	@Column(name = "discount")
	private int discount;
	@Column(name = "imagename")
	private String imagename;
	@Column(name = "image", length = 10000)
	private byte[] image;
	@Column(name = "categories")
	private String categories;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "sale")
	private boolean sale = false;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPriceOrigin() {
		return priceorigin;
	}
	public void setPriceOrigin(int priceorigin) {
		this.priceorigin = priceorigin;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public boolean isSale() {
		return sale;
	}
	public void setSale(boolean sale) {
		this.sale = sale;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", priceOrigin=" + priceorigin + ", discount=" + discount + ", image=" + image + ", categories="
				+ categories + ", quantity=" + quantity + ", sale=" + sale + "]";
	}
}
