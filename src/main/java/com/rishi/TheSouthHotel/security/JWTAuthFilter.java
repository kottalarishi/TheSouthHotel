package com.rishi.TheSouthHotel.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rishi.TheSouthHotel.service.CustomUserDetailsService;
import com.rishi.TheSouthHotel.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    final String authHeader = request.getHeader("Authorization");
	    final String jwtToken;
	    final String userEmail;

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    jwtToken = authHeader.substring(7);
	    userEmail = jwtUtils.extractUsername(jwtToken);

	    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

	        if (jwtUtils.validToken(jwtToken, userDetails)) {
	            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                    userDetails,
	                    null,
	                    userDetails.getAuthorities()
	            );
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            
	            // ✅ This is the correct way to set the context
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	         // DEBUG LOG – confirm Spring Security context is set
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}
