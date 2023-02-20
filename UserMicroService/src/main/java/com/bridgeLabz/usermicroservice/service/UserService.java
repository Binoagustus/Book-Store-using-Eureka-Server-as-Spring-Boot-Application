package com.bridgeLabz.usermicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeLabz.usermicroservice.DTO.LoginDTO;
import com.bridgeLabz.usermicroservice.exception.BookStoreException;
import com.bridgeLabz.usermicroservice.model.CartModel;
import com.bridgeLabz.usermicroservice.model.UserModel;
import com.bridgeLabz.usermicroservice.repository.UserRepository;
import com.bridgeLabz.usermicroservice.utility.JWTUtils;

@Service
public class UserService implements IUserService{

	@Autowired
	UserRepository userRepo;

	@Autowired
	JWTUtils jwtUtils;

	@Override
	public UserModel registerUser(UserModel user) {
		if (userRepo.findByEmail(user.getEmail()).isPresent()) {
			throw new BookStoreException("Mail Id already exists");
		} else {
			return userRepo.save(user);
		}
	}

	@Override
	public void deleteUserByToken(String token) {
		String email = jwtUtils.getEmailFromToken(token);
		Optional<UserModel> user = userRepo.findByEmail(email);
		userRepo.delete(user.get());
	}

	@Override
	public String userLogin(LoginDTO login) {
		String email = login.getEmail();
		String password = login.getPassword();
		Optional<UserModel> user = userRepo.findByEmailAndPassword(email, password);
		Optional<UserModel> userByEmail = userRepo.findByEmail(email);

		if (userByEmail.isPresent()) {
			if (user.isPresent()) {
				String token = jwtUtils.generateToken(login);
				user.get().setLogin(true);
				userRepo.save(user.get());
				return token;
			} else
				throw new BookStoreException("Invalid password. Try Again");
		} else
			throw new BookStoreException("Invalid Email Id");
	}


	@Override
	public void userLogout(String token) {
		String email = jwtUtils.getEmailFromToken(token);
		Optional<UserModel> user = userRepo.findByEmail(email);
		if (user.get().isLogin()) {
			user.get().setLogin(false);
			userRepo.save(user.get());
		} else
			throw new BookStoreException("User Inactive");
	}
	
	@Override
	public boolean isAdmin(String token) {
		Optional<UserModel> user = getUserByToken(token);
		if (user.get().getRole().equals("admin")) {
			return true;
		} else {
			return false;
		}	
	}
	
	@Override
	public boolean checkUserisLoggedIn(String token) {
		Optional<UserModel> user = getUserByToken(token);
		if(user.get().isLogin()) {
			return true;
		} else
			return false;
	}
	
	
	private Optional<UserModel> getUserByToken(String token) {
		String email = jwtUtils.getEmailFromToken(token);
		Optional<UserModel> getByEmail = userRepo.findByEmail(email);
		Optional<UserModel> getByEmailAndPassword = userRepo.findByEmailAndPassword(email,
				getByEmail.get().getPassword());

		if (getByEmail.isPresent()) {
			if (getByEmailAndPassword.isPresent()) {
				return getByEmail;
			} else
				throw new BookStoreException("Password is incorrect");
		} else
			throw new BookStoreException("Email id not exists");
	}

	@Override
	public CartModel getACartForUserByToken(String token) {
		Optional<UserModel> user = getUserByToken(token);
		return user.get().getCart();
	}
}
