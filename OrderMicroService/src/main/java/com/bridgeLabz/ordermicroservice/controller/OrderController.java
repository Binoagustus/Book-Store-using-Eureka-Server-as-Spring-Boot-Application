package com.bridgeLabz.ordermicroservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.ordermicroservice.Response;
import com.bridgeLabz.ordermicroservice.DTO.OrderDTO;
import com.bridgeLabz.ordermicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.ordermicroservice.VO.UserOrderVO;
import com.bridgeLabz.ordermicroservice.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	IOrderService service;
	
	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping("/placeOrder")
	public ResponseEntity<Response> placeOrder(@RequestParam String token, @RequestBody OrderDTO orderDTO) {
		ResponseTemplateVO order = service.placeOrder(token, orderDTO);
		Response response = new Response("Order placed succesfully", order);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}	

	@GetMapping("/viewOrders")
	public ResponseEntity<Response> viewOrders(@RequestParam String token) {
		List<UserOrderVO> order = service.getOrders(token);
		Response response = new Response("All the orders are ", order);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
