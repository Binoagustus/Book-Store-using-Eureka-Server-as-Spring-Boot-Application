package com.bridgeLabz.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.ordermicroservice.model.OrderedBooksModel;

@Repository
public interface OrderedBooksRepository extends JpaRepository<OrderedBooksModel, Integer>{

	@Query(value = "select * from bookstore.ordered_books_model where cart_id = :cartId and order_id = :orderId", nativeQuery = true)
	List<OrderedBooksModel> findAllByCartIdAndOrderId(int cartId, int orderId);
}
