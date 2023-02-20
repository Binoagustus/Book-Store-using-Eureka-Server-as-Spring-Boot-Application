package com.bridgeLabz.ordermicroservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgeLabz.ordermicroservice.DTO.BookDTO;
import com.bridgeLabz.ordermicroservice.DTO.OrderDTO;
import com.bridgeLabz.ordermicroservice.VO.BookModel;
import com.bridgeLabz.ordermicroservice.VO.CartAndBooksModel;
import com.bridgeLabz.ordermicroservice.VO.CartModel;
import com.bridgeLabz.ordermicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.ordermicroservice.VO.UserBookAndQuantityVO;
import com.bridgeLabz.ordermicroservice.VO.UserOrderVO;
import com.bridgeLabz.ordermicroservice.VO.ViewCartVO;
import com.bridgeLabz.ordermicroservice.exception.BookStoreException;
import com.bridgeLabz.ordermicroservice.model.OrderModel;
import com.bridgeLabz.ordermicroservice.model.OrderedBooksModel;
import com.bridgeLabz.ordermicroservice.repository.OrderRepository;
import com.bridgeLabz.ordermicroservice.repository.OrderedBooksRepository;

@Service
public class OrderService implements IOrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OrderedBooksRepository orderedBooksRepo;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public ResponseTemplateVO placeOrder(String token, OrderDTO orderDTO) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);

		if (isLogin) {

			CartAndBooksModel[] cartBookArray = restTemplate
					.getForObject("http://localhost:8003/cart/getCartWithBooks/" + token, CartAndBooksModel[].class);
			List<CartAndBooksModel> cartBook = Arrays.asList(cartBookArray);
			UserBookAndQuantityVO[] bookAndQuantityArray = restTemplate.getForObject(
					"http://localhost:8003/cart/getAllBooksWithQuantity/" + token, UserBookAndQuantityVO[].class);

			if (cartBook.size() == 0) {
				throw new BookStoreException("Cart is empty");
			} else {
				double price = 0;
				int quantity = 0;
				int cartId = 0;

				for (int i = 0; i < cartBook.size(); i++) {
					price = price + (double) cartBook.get(i).getBookPrice();
					quantity = quantity + cartBook.get(i).getQuantity();
					System.out.println(cartBook.get(i).getBookPrice());
					cartId = cartBook.get(i).getCartId();
				}

				OrderModel order = modelMapper.map(orderDTO, OrderModel.class);
				order.setTotalPrice(price);
				order.setCartId(cartId);
				order.setTotalQuantity(quantity);
				orderRepo.save(order);

				/*
				 * Update Books Ordered Table, Decrease Quantity in inventory and Empty the cart
				 */
				for (int i = 0; i < cartBook.size(); i++) {

					OrderedBooksModel booksOrdered = new OrderedBooksModel();
					booksOrdered.setBookId(cartBook.get(i).getBookId());
					booksOrdered.setOrderId(order.getOrderId());
					booksOrdered.setCartId(order.getCartId());
					booksOrdered.setQuantity(cartBook.get(i).getQuantity());
					orderedBooksRepo.save(booksOrdered);
					System.out.println("books ordered table is done" + i + "times");

					int boughtQuantity = restTemplate.getForObject("http://localhost:8003/cart/findQuantity/" + token
							+ "/" + cartId + "/" + cartBook.get(i).getBookId(), Integer.class);
					BookModel book = restTemplate.getForObject(
							"http://localhost:8002/book/findByBookId/" + cartBook.get(i).getBookId(), BookModel.class);

					int totalBookQuantity = book.getQuantity();
					totalBookQuantity = totalBookQuantity - boughtQuantity;
					restTemplate.put(
							"http://localhost:8002/book/updateQuantity/" + totalBookQuantity + "/" + book.getBookId(),
							HttpMethod.PUT);
				}

				ResponseTemplateVO response = new ResponseTemplateVO();
				response.setOrderId(order.getOrderId());
				response.setName(orderDTO.getUserName());
				response.setAddress(orderDTO.getAddress());
				List<UserBookAndQuantityVO> bookAndQuantityList = Arrays.asList(bookAndQuantityArray);
				response.setViewBooks(bookAndQuantityList);

				restTemplate.delete("http://localhost:8003/cart/emptyCart/" + token);
				System.out.println("Cart book is emptied");

				return response;
			}
		} else
			throw new BookStoreException("User is not logged in");
	}

	@Override
	public List<UserOrderVO> getOrders(String token) {
		boolean isLogin = restTemplate.getForObject("http://localhost:8001/user/checkLogIn/" + token, Boolean.class);

		if (isLogin) {
			CartModel cart = restTemplate.getForObject("http://localhost:8001/user/getCart/" + token, CartModel.class);
			int cartId = cart.getCartId();
			List<OrderModel> orderModel = orderRepo.findAllByCartId(cartId);
			
			List<UserOrderVO> orderList = new ArrayList<>();

			for (int i = 0; i < orderModel.size(); i++) {
				List<ViewCartVO> listView = new ArrayList<>();
				List<OrderedBooksModel> cartBook = orderedBooksRepo.findAllByCartIdAndOrderId(cartId, orderModel.get(i).getOrderId());
				cartBook.stream().forEach(order -> System.out.println("cart List" + order.toString()));

				for (int j = 0; j < cartBook.size(); j++) {
					ViewCartVO cartview = new ViewCartVO();
					BookModel book = restTemplate.getForObject(
							"http://localhost:8002/book/findByBookId/" + cartBook.get(j).getBookId(), BookModel.class);
					System.out.println(book.toString());
					cartview.setBook(modelMapper.map(book, BookDTO.class));
					cartview.setQuantity(cartBook.get(j).getQuantity());
					listView.add(cartview);
					System.out.println("bookview" + listView.toString());
				}
				UserOrderVO userOrderView = new UserOrderVO();
				userOrderView.setAddress(orderModel.get(i).getAddress());
				userOrderView.setViewBooks(listView);
				userOrderView.setName(orderModel.get(i).getUserName());
				userOrderView.setOrderId(orderModel.get(i).getOrderId());
				orderList.add(userOrderView);
				System.out.println(userOrderView.toString());
			}
			orderList.stream().forEach(order -> System.out.println("order List" + order.toString()));
			return orderList;
		} else
			throw new BookStoreException("User is not logged in");
	}

}
