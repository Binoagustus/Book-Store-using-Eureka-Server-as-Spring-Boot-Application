package com.bridgeLabz.usermicroservice.service;

import com.bridgeLabz.usermicroservice.DTO.LoginDTO;
import com.bridgeLabz.usermicroservice.model.CartModel;
import com.bridgeLabz.usermicroservice.model.UserModel;

public interface IUserService {

	void userLogout(String token);

	String userLogin(LoginDTO login);

	UserModel registerUser(UserModel user);

	void deleteUserByToken(String token);
	
	boolean isAdmin(String token);

	boolean checkUserisLoggedIn(String token);

	CartModel getACartForUserByToken(String token);
}
