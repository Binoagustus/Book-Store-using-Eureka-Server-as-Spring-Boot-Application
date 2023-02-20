package com.bridgeLabz.ordermicroservice.VO;

import com.bridgeLabz.ordermicroservice.DTO.BookDTO;

public class UserBookAndQuantityVO {

	private BookDTO book;
	private int quantity;
	
	public BookDTO getBook() {
		return book;
	}
	public void setBook(BookDTO book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
}
