package com.shakir.hotelCorporateHeaven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shakir.hotelCorporateHeaven.exception.UserAlreadyExistException;
import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.request.LoginRequest;
import com.shakir.hotelCorporateHeaven.response.JwtReponse;
import com.shakir.hotelCorporateHeaven.security.jwt.JwtUtils;
import com.shakir.hotelCorporateHeaven.security.user.HotelUserDetails;
import com.shakir.hotelCorporateHeaven.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final AuthenticationManager authManager;
	private final JwtUtils jwtUtils;
	
	@PostMapping("/registerUser")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try {
			userService.registerUser(user);
			return ResponseEntity.ok("User Registered Succesfully");
		}catch(UserAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in registering user "+e.getMessage()+user.getFirstName()+user.getEmail());
		}
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication=authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtUtils.generateJwtTokenForUser(authentication);
		HotelUserDetails userDetails=(HotelUserDetails) authentication.getPrincipal();
		List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.toList();
		return ResponseEntity.ok(new JwtReponse(
				userDetails.getId(),
				userDetails.getEmail(),
				jwt,
				roles));
	}

}
