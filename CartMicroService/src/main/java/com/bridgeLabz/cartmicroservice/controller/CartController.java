package com.bridgeLabz.cartmicroservice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.cartmicroservice.Response;
import com.bridgeLabz.cartmicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.cartmicroservice.model.CartAndBooksModel;
import com.bridgeLabz.cartmicroservice.service.IcartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	IcartService service;
	
	Logger logger = LoggerFactory.getLogger(CartController.class);

	@PostMapping("/addBooksToCart")
	public ResponseEntity<Response> addBooksToCart(@RequestParam String token, 
			@RequestParam String bookName) {

		ResponseTemplateVO cart = service.addBooksToCart(token, bookName);
		Response response = new Response("Book has been added to Cart", cart);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteBooksFromCart")
	public ResponseEntity<Response> removeBooksFromCart(@RequestParam String token, 
			@RequestParam int bookId) {

		service.removeBooksFromCart(token, bookId);
		Response response = new Response("Book has been removed from Cart");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
		
	@PutMapping("/addQuantity")
	public ResponseEntity<Response> increaseQuantityOfBooks(@RequestParam String token,
			@RequestParam int quantity, @RequestParam int bookId) {
		
		String book = service.addQuantityOfBooks(token, quantity, bookId);
		Response response = new Response(quantity + " books have been added ", book);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/decreaseQuantity")
	public ResponseEntity<Response> decreaseQuantityOfBooks(@RequestParam String token, 
			@RequestParam int quantity, @RequestParam int bookId) {
		
		String book = service.subtractQuantityOfBooks(token, quantity, bookId);
		Response response = new Response(quantity + " books have beeen removed", book);
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}

	@GetMapping("/viewAllBooks")
	public ResponseEntity<Response> viewBooksOfaUser(@RequestParam String token) {
		List<ResponseTemplateVO> books = service.viewAllBooksFromCart(token);
		Response response = new Response("All the Books in the cart are", books);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAllBooksWithQuantity/{token}")
	public List<ResponseTemplateVO> getAllBooks(@PathVariable String token) {
		List<ResponseTemplateVO> books = service.viewAllBooksFromCart(token);
		return books;
	}
	
	@GetMapping("/getCartWithBooks/{token}")
	public List<CartAndBooksModel> getCartWithBooks(@PathVariable String token) {
		List<CartAndBooksModel> cartAndBooks = service.getAllCartWithBooks(token);
		return cartAndBooks;
	}
	
	@GetMapping("/findQuantity/{token}/{cartId}/{bookId}")
	public int findQuantityByCartIdAndBookId(@PathVariable String token, @PathVariable int cartId, @PathVariable int bookId) {
		int quantity = service.getQuantityByCartIdAndBookId(token, cartId, bookId);
		return quantity;
	}
	
	@DeleteMapping("/emptyCart/{token}")
	public void emptyCartByToken(@PathVariable String token) {
		service.deleteCartWithBooksByToken(token);
	}
}
