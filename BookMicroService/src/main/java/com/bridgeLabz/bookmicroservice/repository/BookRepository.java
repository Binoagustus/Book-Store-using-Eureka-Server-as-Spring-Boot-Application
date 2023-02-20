package com.bridgeLabz.bookmicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeLabz.bookmicroservice.model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer>{

	Optional<BookModel> deleteByBookName(String bookName);
	
	Optional<BookModel> findByBookName(String bookName);
	
	@Transactional
	@Modifying
	@Query(value = "update bookstore.book_model set quantity= :quantity where book_id= :bookId", nativeQuery = true)
	void updateQuantityByBookId(int quantity, int bookId);
}
