package com.bridgeLabz.ordermicroservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderedBooksModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderedBookId;
	private int orderId;
	private int cartId;
	private int bookId;
	private int quantity;

	public int getOrderedBookId() {
		return orderedBookId;
	}

	public void setOrderedBookId(int orderedBookId) {
		this.orderedBookId = orderedBookId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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
