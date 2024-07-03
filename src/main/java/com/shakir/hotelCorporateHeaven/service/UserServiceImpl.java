package com.shakir.hotelCorporateHeaven.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shakir.hotelCorporateHeaven.exception.UserAlreadyExistException;
import com.shakir.hotelCorporateHeaven.model.Role;
import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.repository.RoleRepository;
import com.shakir.hotelCorporateHeaven.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	@Override
	public User registerUser(User user) throws UserAlreadyExistException {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistException(user.getEmail()+" already exist ");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole=roleRepository.findByName("ROLE_USER").get();
		user.setRoles(Collections.singletonList(userRole));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}
	@Transactional
	@Override
	public void deleteUser(String email) {
		User theUser=getUser(email);
		if(theUser!=null) {
			userRepository.deleteByEmail(email);
		}
		
		
	}

	@Override
	public User getUser(String email) {
		return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
	}

}
