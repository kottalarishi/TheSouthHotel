package com.rishi.TheSouthHotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rishi.TheSouthHotel.exception.OurException;
import com.rishi.TheSouthHotel.repo.UserRepo;
@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByEmail(username).orElseThrow(()-> new OurException("Username/emailNotFound"));
	}
	
	
}

