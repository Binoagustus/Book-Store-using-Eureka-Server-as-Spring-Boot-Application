package com.bridgeLabz.bookmicroservice.service;

import java.util.List;

import com.bridgeLabz.bookmicroservice.DTO.BookDTO;
import com.bridgeLabz.bookmicroservice.model.BookModel;

public interface IBookService {

	void addBook(String token, BookModel book);

	void removeBook(String token, String bookName);

	BookDTO updateBookDetails(String token, BookModel book);

	List<BookDTO> fetchAllBooks();

	BookModel getABookByBookName(String bookName);
	
	BookModel getABookByBookId(int bookId);

	void updateBookQuantity(int quantity, int bookId);
}
