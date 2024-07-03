package com.shakir.hotelCorporateHeaven.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	@GetMapping("/allUsers")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getUsers(){
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.FOUND);
	}
	@GetMapping("/user/{email}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
		try {
			User theUser=userService.getUser(email);
			return ResponseEntity.ok(theUser);
		}catch(UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in fetching User");
		}
		
	}
	@DeleteMapping("/deleteUser/{email}")
	@PreAuthorize("hasRole('ROLE_ADMIN')or  (hasRole('ROLE_USER') and #email== principal.username)")
	public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
		try {
			userService.deleteUser(email);
			return ResponseEntity.ok("User deleted succesfully");
		}catch(UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in deleting User");
		}
	}
	

}
