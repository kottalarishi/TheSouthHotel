package com.rishi.TheSouthHotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.dto.UserDto;
import com.rishi.TheSouthHotel.service.interfac.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Responses> getAllusers(){
		Responses responses= iUserService.getAllUsers();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@GetMapping("/getById/{userId}")
	public ResponseEntity<Responses> getUsersById(@PathVariable("userId")String userId){
		Responses responses= iUserService.getUserById(userId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@DeleteMapping("/deleteById/{userId}")
	public ResponseEntity<Responses> deleteuser(@PathVariable("userId") String userId) {
	    Responses responses = iUserService.deleteUser(userId);
	    return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	
	@GetMapping("/getLoggedInprofile")
	public ResponseEntity<Responses> getLoggedInUserProfile(){
		
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String email=authentication.getName();
		
		
		Responses responses= iUserService.getMyInfo(email);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@GetMapping("/getUserBookings/{userId}")
	public ResponseEntity<Responses> getUsersBookingHistory(@PathVariable("userId")String userId){
		Responses responses= iUserService.getUserbookingHistory(userId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	
	@PutMapping("/update/{userId}")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	public ResponseEntity<Responses> updateUserProfile(@PathVariable Long userId, @RequestBody UserDto updatedUserDto) {
	    Responses response = iUserService.updateUserProfile(userId, updatedUserDto);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	
	
}
