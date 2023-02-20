package com.bridgeLabz.ordermicroservice.service;

import java.util.List;

import com.bridgeLabz.ordermicroservice.DTO.OrderDTO;
import com.bridgeLabz.ordermicroservice.VO.ResponseTemplateVO;
import com.bridgeLabz.ordermicroservice.VO.UserOrderVO;

public interface IOrderService {

	ResponseTemplateVO placeOrder(String token, OrderDTO orderDTO);

	List<UserOrderVO> getOrders(String token);
}
