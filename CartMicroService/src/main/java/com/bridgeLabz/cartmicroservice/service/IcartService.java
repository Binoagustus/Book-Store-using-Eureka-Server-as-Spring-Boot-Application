package com.bridgeLabz.cartmicroservice.service;

import java.util.List;

import com.bridgeLabz.cartmicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.cartmicroservice.model.CartAndBooksModel;

public interface IcartService {

	ResponseTemplateVO addBooksToCart(String token, String bookName);

	void removeBooksFromCart(String token, int bookId);

	String addQuantityOfBooks(String token, int quantity, int bookId);

	String subtractQuantityOfBooks(String token, int quantity, int bookId);

	List<ResponseTemplateVO> viewAllBooksFromCart(String token);

	List<CartAndBooksModel> getAllCartWithBooks(String token);

	int getQuantityByCartIdAndBookId(String token, int cartId, int bookId);

	void deleteCartWithBooksByToken(String token);
}
