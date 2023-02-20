package com.bridgeLabz.cartmicroservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgeLabz.cartmicroservice.DTO.BookDTO;
import com.bridgeLabz.cartmicroservice.VO.BookModel;
import com.bridgeLabz.cartmicroservice.VO.CartModel;
import com.bridgeLabz.cartmicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.cartmicroservice.exception.BookStoreException;
import com.bridgeLabz.cartmicroservice.model.CartAndBooksModel;
import com.bridgeLabz.cartmicroservice.repository.CartAndBookRepository;

@Service
public class CartService implements IcartService{

	@Autowired
	CartAndBookRepository cartBookRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public ResponseTemplateVO addBooksToCart(String token, String bookName) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	

		if (isLogin) {
			BookModel book = restTemplate.getForObject("http://localhost:8002/book/findBook/" + bookName, BookModel.class);
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
			
			if(cartBookRepo.findByCartIdAndBookId(cart.getCartId(), book.getBookId()).isPresent()) {
				throw new BookStoreException("Book is already available in the cart");
			} else {
				CartAndBooksModel cartAndBook = new CartAndBooksModel();
				cartAndBook.setQuantity(1);
				cartAndBook.setBookId(book.getBookId());
				cartAndBook.setCartId(cart.getCartId());
				cartAndBook.setBookPrice(book.getBookPrice());
				cartBookRepo.save(cartAndBook);
				ResponseTemplateVO responseTemplate = new ResponseTemplateVO();
				responseTemplate.setBook(modelMapper.map(book, BookDTO.class));
				responseTemplate.setQuantity(1);
				return responseTemplate;
			}	
		} else
			throw new BookStoreException("Login first");
	}

	@Override
	public void removeBooksFromCart(String token, int bookId) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	

		if (isLogin) {
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
			Optional<CartAndBooksModel> cartBook = cartBookRepo.findByCartIdAndBookId(cart.getCartId(), bookId);
			
			if (cartBook.isPresent()) {
				cartBookRepo.deleteById(cartBook.get().getCartBookId());
			} else
				throw new BookStoreException("Book is not in your cart");
		} else
			throw new BookStoreException("Login first");
	}
	
	
	@Override
	public String addQuantityOfBooks(String token, int increaseQuantity, int bookId) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	

		if (isLogin) {
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);	
			Optional<CartAndBooksModel> cartBook = cartBookRepo.findByCartIdAndBookId(cart.getCartId(), bookId);
			
			if(cartBook.isPresent()) {
				int quantity = cartBook.get().getQuantity();
				quantity = quantity + increaseQuantity;
				cartBook.get().setQuantity(quantity);
				cartBookRepo.save(cartBook.get());
				return "Quantity available " + quantity;
			} else
				throw new BookStoreException("Book not in your cart");
		} else
			throw new BookStoreException("User is not loggeed in");
	}

	@Override
	public String subtractQuantityOfBooks(String token, int decreaseQuantity, int bookId) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	

		if (isLogin) {
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
			Optional<CartAndBooksModel> cartBook = cartBookRepo.findByCartIdAndBookId(cart.getCartId(), bookId);
			
			if(cartBook.isPresent()) {
				int quantity = cartBook.get().getQuantity();
				if(quantity == 1 || quantity - decreaseQuantity == 0) {
					throw new BookStoreException("Quantity cannot be zero. Try deleting the book");
				} else {
					quantity = quantity - decreaseQuantity;
					cartBook.get().setQuantity(quantity);
					cartBookRepo.save(cartBook.get());
					return "Quantity available " + quantity;
				}
			} else
				throw new BookStoreException("Book not in your cart");
		} else
			throw new BookStoreException("User is not loggeed in");
	}

	@Override
	public List<ResponseTemplateVO> viewAllBooksFromCart(String token) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	

		if(isLogin) {
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
			int cartId = cart.getCartId();
			List<CartAndBooksModel> cartBook = cartBookRepo.findByCartId(cartId);
			List<ResponseTemplateVO> listView = new ArrayList<>();
			
			for(int i = 0; i < cartBook.size(); i++) {
				
				ResponseTemplateVO cartview = new ResponseTemplateVO();
				BookModel book = restTemplate.getForObject("http://localhost:8002/book/findByBookId/" + cartBook.get(i).getBookId(), BookModel.class);
				cartview.setBook((modelMapper.map(book, BookDTO.class))); ;
				cartview.setQuantity(cartBook.get(i).getQuantity());
				listView.add(cartview);
				System.out.println(cartview.toString());
			}
			return listView;
		} else
			throw new BookStoreException("User is not logged in");
	}

	@Override
	public List<CartAndBooksModel> getAllCartWithBooks(String token) {
		CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
		int cartId = cart.getCartId();
		List<CartAndBooksModel> cartBook = cartBookRepo.findByCartId(cartId);
		return cartBook;
	}

	@Override
	public int getQuantityByCartIdAndBookId(String token, int cartId, int bookId) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);	
		
		if(isLogin) {
			return cartBookRepo.findQuantityByCartIdAndBookId(cartId, bookId);
		} else
			throw new BookStoreException("User is not logged in");
	}

	@Override
	public void deleteCartWithBooksByToken(String token) {
		CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
		int cartId = cart.getCartId();
		cartBookRepo.deleteAllByCartId(cartId);
	}
}
