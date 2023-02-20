package com.bridgeLabz.ordermicroservice.VO;

public class BookModel {

	private int bookId;
	private String bookName;
	private String bookAuthor;
	private int quantity;
	private float bookPrice;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	@Override
	public String toString() {
		return "BookModel [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", quantity="
				+ quantity + ", bookPrice=" + bookPrice + "]";
	}
}
