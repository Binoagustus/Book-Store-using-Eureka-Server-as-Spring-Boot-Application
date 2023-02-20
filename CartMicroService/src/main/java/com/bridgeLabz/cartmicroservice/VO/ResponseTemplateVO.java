package com.bridgeLabz.cartmicroservice.VO;

import com.bridgeLabz.cartmicroservice.DTO.BookDTO;

public class ResponseTemplateVO {

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
