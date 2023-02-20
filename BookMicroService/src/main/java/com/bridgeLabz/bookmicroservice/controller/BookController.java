package com.bridgeLabz.bookmicroservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.bookmicroservice.Response;
import com.bridgeLabz.bookmicroservice.DTO.BookDTO;
import com.bridgeLabz.bookmicroservice.model.BookModel;
import com.bridgeLabz.bookmicroservice.service.IBookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	IBookService service;

	Logger logger = LoggerFactory.getLogger(BookController.class);

	@PostMapping("/addBook")
	public ResponseEntity<Response> addBooks(@RequestParam String token, @RequestBody BookModel book) {

		service.addBook(token, book);
		Response response = new Response("Book Added Succesfully", book);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteBook")
	public ResponseEntity<Response> removeBook(@RequestParam String token, @RequestParam String bookName) {

		service.removeBook(token, bookName);
		Response response = new Response("Book Deleted Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/updateBook")
	public ResponseEntity<Response> updateBookDetails(@RequestParam String token, 
			@RequestBody BookModel book) {

		BookDTO updatedBook = service.updateBookDetails(token, book);
		Response response = new Response("Book Updated Succesfully", updatedBook);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/fetchAllBooks")
	public ResponseEntity<Response> getAllBooks() {
		List<BookDTO> listOfBooks = service.fetchAllBooks();
		Response response = new Response("List Of Books", listOfBooks);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/findBook/{bookName}")
	public BookModel getBook(@PathVariable String bookName) {
		BookModel book = service.getABookByBookName(bookName);
		return book;
	}
	
	@GetMapping("/findByBookId/{bookId}")
	public BookModel getBookByBookId(@PathVariable int bookId) {
		return service.getABookByBookId(bookId);
	}
	
	@PutMapping("/updateQuantity/{quantity}/{bookId}")
	public void updateBookQuantity(@PathVariable int quantity, @PathVariable int bookId) {
		service.updateBookQuantity(quantity, bookId);
	}
}
