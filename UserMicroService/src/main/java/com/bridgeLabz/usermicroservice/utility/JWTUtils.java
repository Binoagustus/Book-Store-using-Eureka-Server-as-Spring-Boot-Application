package com.bridgeLabz.usermicroservice.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeLabz.usermicroservice.DTO.LoginDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

	@Autowired
	LoginDTO loginDTO;
	
	public String secretKey = "SecretKey";
	
	public String generateToken(LoginDTO loginDTO) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", loginDTO.getEmail());
		claims.put("password", loginDTO.getPassword());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
	public String getEmailFromToken(String token) {
		Map<String, Object> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		System.out.println(claims.get("email"));
		return claims.get("email").toString();
	}
}
