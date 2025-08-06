package com.rishi.TheSouthHotel.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rishi.TheSouthHotel.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



@Service
public class JWTUtils {
	
	

	
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
	
	private final SecretKey key;
	
	public JWTUtils() {
		String secreteString = "1w2e3r4t5y6u7i8o9p0a1s2d3f4g5h6j7k8l";

		this.key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secreteString.getBytes(StandardCharsets.UTF_8));

	}
	
	public String genrateToken(UserDetails userDetails) {
	    if (!(userDetails instanceof User)) {
	        throw new IllegalArgumentException("UserDetails is not of type User");
	    }

	    User user = (User) userDetails;

	    return Jwts.builder()
	            .subject(user.getUsername())
	            .claim("userId", user.getId()) // ✅ Add this line
	            .claim("authorities", user.getAuthorities().stream()
	                    .map(GrantedAuthority::getAuthority)
	                    .toList())
	            .issuedAt(new Date(System.currentTimeMillis()))
	            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	            .signWith(key)
	            .compact();
	}




	public String extractUsername(String token) {
		
		return extractClaims(token ,Claims::getSubject);
	}
	
	private <T> T extractClaims(String token,Function<Claims,T>claimsTFunction) {
		return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
	}
	
	public boolean validToken(String token, UserDetails userDetails) {
		final String userName= extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}

	
}
