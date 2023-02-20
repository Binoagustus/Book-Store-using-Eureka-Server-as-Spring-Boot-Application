package com.bridgeLabz.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.ordermicroservice.model.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer>{

	@Query(value = "SELECT * FROM bookstore.order_model where cart_id= :cartId", nativeQuery = true)
	List<OrderModel> findAllByCartId(int cartId);
}
