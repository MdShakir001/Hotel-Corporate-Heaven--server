package com.shakir.hotelCorporateHeaven.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class HotelUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email).orElseThrow(()->new
				UsernameNotFoundException("username not found"));
		return HotelUserDetails.buildUserDetails(user);
	}

}
