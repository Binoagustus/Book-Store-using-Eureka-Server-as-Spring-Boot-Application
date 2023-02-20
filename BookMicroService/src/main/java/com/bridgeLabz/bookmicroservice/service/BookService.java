package com.bridgeLabz.bookmicroservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgeLabz.bookmicroservice.DTO.BookDTO;
import com.bridgeLabz.bookmicroservice.exception.BookStoreException;
import com.bridgeLabz.bookmicroservice.model.BookModel;
import com.bridgeLabz.bookmicroservice.repository.BookRepository;

@Service
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public void addBook(String token, BookModel book) {
		boolean isAdmin = restTemplate.getForObject("http://localhost:8001/user/checkAdmin/" + token, Boolean.class);

		if (isAdmin) {
			if (bookRepo.findByBookName(book.getBookName()).isPresent()) {
				throw new BookStoreException("Book Already exists");
			} else
				bookRepo.save(book);
		} else
			throw new BookStoreException("You are not admin");
	}

	@Override
	public void removeBook(String token, String bookName) {
		boolean isAdmin = restTemplate.getForObject("http://localhost:8001/user/checkAdmin/" + token, Boolean.class);

		if (isAdmin) {
			if (bookRepo.findByBookName(bookName).isPresent()) {
				bookRepo.deleteByBookName(bookName);
			} else
				throw new BookStoreException("Book Already exists");

		} else
			throw new BookStoreException("You are not Admin");
	}

	@Override
	public BookDTO updateBookDetails(String token, BookModel book) {

		boolean isAdmin = restTemplate.getForObject("http://localhost:8001/user/checkAdmin/" + token, Boolean.class);

		if (isAdmin) {

			Optional<BookModel> foundBook = bookRepo.findByBookName(book.getBookName());
			if (foundBook.isPresent()) {
				if (book.getBookAuthor() == null) {
					foundBook.get().setBookAuthor(foundBook.get().getBookAuthor());
				} else
					foundBook.get().setBookAuthor(book.getBookAuthor());

				if (book.getBookId() == 0) {
					foundBook.get().setBookId(foundBook.get().getBookId());
				} else
					foundBook.get().setBookId(book.getBookId());

				if (book.getBookName() == null) {
					foundBook.get().setBookName(foundBook.get().getBookName());
				} else
					foundBook.get().setBookName(book.getBookName());

				if (book.getBookPrice() == 0) {
					foundBook.get().setBookPrice(foundBook.get().getBookPrice());
				} else
					foundBook.get().setBookPrice(book.getBookPrice());

				if (book.getQuantity() == 0) {
					foundBook.get().setQuantity(foundBook.get().getQuantity());
				} else
					foundBook.get().setQuantity(book.getQuantity());

				bookRepo.save(foundBook.get());
				return modelMapper.map(foundBook.get(), BookDTO.class);
			} else
				throw new BookStoreException("Book doesn't exist");

		} else
			throw new BookStoreException("You are not Admin");
	}

	@Override
	public List<BookDTO> fetchAllBooks() {
		List<BookModel> books = bookRepo.findAll();
		List<BookDTO> bookDTO = books.stream().map(book -> modelMapper.map(book, BookDTO.class))
				.collect(Collectors.toList());
		return bookDTO;
	}

	@Override
	public BookModel getABookByBookName(String bookName) {
		Optional<BookModel> book = bookRepo.findByBookName(bookName);
		
		if(book.isPresent() && book.get().getQuantity() >= 1) {
			return book.get();
		} else
			throw new BookStoreException("Book Out Of Stock");
	}

	@Override
	public BookModel getABookByBookId(int bookId) {
		return bookRepo.findById(bookId).get();
	}

	@Override
	public void updateBookQuantity(int quantity, int bookId) {
		bookRepo.updateQuantityByBookId(quantity, bookId);
	}

}
