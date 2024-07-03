package com.shakir.hotelCorporateHeaven.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class JwtReponse {
	private Long id;
	private String email;
	private String jwt;
	private String type="Bearer";
	private List<String> roles;
	public JwtReponse(Long id, String email, String jwt, List<String> roles) {
		super();
		this.id = id;
		this.email = email;
		this.jwt = jwt;
		this.roles = roles;
	}

	

}
