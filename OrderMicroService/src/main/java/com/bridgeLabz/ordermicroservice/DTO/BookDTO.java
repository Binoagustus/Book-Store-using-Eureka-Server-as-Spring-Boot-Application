package com.bridgeLabz.ordermicroservice.DTO;

public class BookDTO {

	private String bookName;
	private String bookAuthor;
	private float bookPrice;
	
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	@Override
	public String toString() {
		return "bookName=" + bookName + "  " +  "bookAuthor=" + bookAuthor + "  " + "bookPrice=" + bookPrice;
	}	
}
