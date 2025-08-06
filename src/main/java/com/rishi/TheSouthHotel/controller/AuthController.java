package com.rishi.TheSouthHotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.TheSouthHotel.dto.LoginRequest;
import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.entity.User;
import com.rishi.TheSouthHotel.service.interfac.IUserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Responses> register(@RequestBody User user){
		Responses responses =userService.register(user);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Responses> login(@RequestBody LoginRequest loginRequest){
		Responses responses =userService.login(loginRequest);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
}
