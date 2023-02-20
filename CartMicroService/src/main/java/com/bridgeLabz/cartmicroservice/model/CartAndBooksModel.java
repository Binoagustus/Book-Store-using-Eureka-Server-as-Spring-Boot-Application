package com.bridgeLabz.cartmicroservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartAndBooksModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartBookId;
	private int bookId;
	private float bookPrice;
	private int quantity;
	private int cartId;
	
	public int getCartBookId() {
		return cartBookId;
	}
	public void setCartBookId(int cartBookId) {
		this.cartBookId = cartBookId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public float getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
}
