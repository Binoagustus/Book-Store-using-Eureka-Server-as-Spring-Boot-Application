package com.bridgeLabz.cartmicroservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeLabz.cartmicroservice.model.CartAndBooksModel;

@Repository
public interface CartAndBookRepository extends JpaRepository<CartAndBooksModel, Integer>{

	@Query(value = "select * from bookstore.cart_and_books_model where cart_id= :cartId and book_id= :bookId", nativeQuery = true)
	Optional<CartAndBooksModel> findByCartIdAndBookId(int cartId, int bookId);
	
	@Query(value = "SELECT * FROM bookstore.cart_and_books_model where cart_id= :cartId", nativeQuery = true)
	List<CartAndBooksModel> findByCartId(int cartId);

	@Query(value = "select quantity from bookstore.cart_and_books_model where cart_id= :cartId and book_id= :bookId", nativeQuery = true)
	int findQuantityByCartIdAndBookId(int cartId, int bookId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from bookstore.cart_and_books_model where cart_id= :cartId", nativeQuery = true)
	void deleteAllByCartId(int cartId);
}
