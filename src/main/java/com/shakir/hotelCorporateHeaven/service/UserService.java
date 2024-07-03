package com.shakir.hotelCorporateHeaven.service;

import java.util.List;

import com.shakir.hotelCorporateHeaven.exception.UserAlreadyExistException;
import com.shakir.hotelCorporateHeaven.model.User;

public interface UserService {
	User registerUser(User user) throws UserAlreadyExistException;
	List<User> getAllUsers();
	void deleteUser(String email);
	User getUser(String email) ;
}
