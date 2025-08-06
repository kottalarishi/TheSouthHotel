package com.rishi.TheSouthHotel.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishi.TheSouthHotel.dto.LoginRequest;
import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.dto.UserDto;
import com.rishi.TheSouthHotel.entity.Booking;
import com.rishi.TheSouthHotel.entity.User;
import com.rishi.TheSouthHotel.exception.OurException;
import com.rishi.TheSouthHotel.repo.UserRepo;
import com.rishi.TheSouthHotel.service.interfac.IUserService;
import com.rishi.TheSouthHotel.utils.JWTUtils;
import com.rishi.TheSouthHotel.utils.Utils;

import jakarta.transaction.Transactional;






@Service
public class UserService  implements IUserService{

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	

	
	@Override
	public Responses register(User user) {
	
		Responses responses = new Responses();
		
		
		try {
			
			
			if(user.getRole()==null|| user.getRole().isBlank()) {
				user.setRole("USER");
			}
			
			if(userRepo.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail()+"Already Exists");
			}
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser=userRepo.save(user);
			UserDto userDto= Utils.mapUserEntityToUserDto(savedUser);
			responses.setStatusCode(200);
			responses.setUser(userDto);
			
			
		}
		catch(OurException e){
			responses.setStatusCode(400);
			responses.setMessage(e.getMessage());
		}
		catch(Exception e) {
			
			responses.setStatusCode(500);
			responses.setMessage("Error occured during  user resgistration "+e.getMessage());
		}
		
		return responses;
	}

	@Override
	public Responses login(LoginRequest loginRequest) {
		Responses responses= new Responses();
		
		try {
			
			
			authenticationManager.authenticate(
				    new UsernamePasswordAuthenticationToken(
				        loginRequest.getEmail(),
				        loginRequest.getPassword()
				    )
				);
			
			var user = userRepo.findByEmail(loginRequest.getEmail())
	                   .orElseThrow(() -> new OurException("user not found"));
			
			var token = jwtUtils.genrateToken(user);
			
			responses.setStatusCode(200);
			responses.setToken(token);
			responses.setRole(user.getRole());
			responses.setExpirationTime("7 days");
			responses.setMessage("successful");

			
		}catch(OurException e) {
			
			responses.setStatusCode(400);
			responses.setMessage(e.getMessage());
			
		}catch(Exception e) {
			responses.setStatusCode(500);
			responses.setMessage("Error occured during user login "+e.getMessage());
			
		}
		return responses;
	}

	@Override
	public Responses getAllUsers() {
		
		Responses responses= new Responses();

			
		try {
			List<User> users= userRepo.findAll();
			 
			List<UserDto> userDtos= Utils.mapUserListEntityToUserListDto(users);
			responses.setStatusCode(200);
			responses.setMessage("Successful");
			responses.setUserDtos(userDtos);	
		}
		catch(OurException e) {
			
			responses.setStatusCode(500);
			responses.setMessage("Error getting all user "+e.getMessage());
			
		}catch(Exception e) {
			responses.setStatusCode(500);
			responses.setMessage("Error getting all users "+e.getMessage());
		}
		return responses;
	}

	@Override
	public Responses getUserbookingHistory(String userId) {
		Responses responses= new Responses();

			try {
				
				User user = userRepo.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("user not found"));
				UserDto userDto= Utils.mapUserEntityToUserDTOPLusUserBookingsAndRoom(user);
				responses.setStatusCode(200);
				responses.setMessage("Successful");
				responses.setUser(userDto);	
				
			}catch(OurException e) {
				
				responses.setStatusCode(404);
				responses.setMessage(e.getMessage());
				
			}catch(Exception e) {
				
				responses.setStatusCode(500);
				responses.setMessage("Error in getting booking history "+e.getMessage());
				
			}
		
		return responses;
	}

	@Transactional
	@Override
	public Responses deleteUser(String userId) {
		Responses responses= new Responses();

		try {
			
			User user =userRepo.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("user not found"));
			  for (Booking booking : user.getBookings()) {
		            booking.setUser(null);
		        }
		        user.getBookings().clear();

		        userRepo.delete(user);			responses.setStatusCode(200);
			responses.setMessage("Successful");
		
			
		}catch(OurException e) {
			
			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());
			
		}catch(Exception e) {
			
			responses.setStatusCode(500);
			responses.setMessage("Error in deleting user "+e.getMessage());
			
		}
	
	return responses;
	}

	@Override
	public Responses getUserById(String userId) {
		Responses responses= new Responses();

		try {
			
			 User user =userRepo.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("user not found"));
			 UserDto userDto=Utils.mapUserEntityToUserDto(user);
			responses.setStatusCode(200);
			responses.setMessage("Successful");
			responses.setUser(userDto);
		
			
		}catch(OurException e) {
			
			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());
			
		}catch(Exception e) {
			
			responses.setStatusCode(500);
			responses.setMessage("Error in deleting user "+e.getMessage());
			
		}
	
	return responses;	}

	@Override
	public Responses getMyInfo(String email) {
		Responses responses= new Responses();

		try {
			
			User user= userRepo.findByEmail(email).orElseThrow(()-> new OurException("user not found"));
			 UserDto userDto=Utils.mapUserEntityToUserDto(user);
			responses.setStatusCode(200);
			responses.setMessage("Successful");
			responses.setUser(userDto);
		
			
		}catch(OurException e) {
			
			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());
			
		}catch(Exception e) {
			
			responses.setStatusCode(500);
			responses.setMessage("Error in deleting user "+e.getMessage());
			
		}
	
	return responses;
	}
	
	@Override
	public Responses updateUserProfile(Long userId, UserDto updatedUserDto) {
	    Responses responses = new Responses();

	    try {
	        User user = userRepo.findById(userId)
	                .orElseThrow(() -> new OurException("user not found"));

	        // Update fields if provided
	        if (updatedUserDto.getName() != null) user.setName(updatedUserDto.getName());
	        if (updatedUserDto.getEmail() != null) user.setEmail(updatedUserDto.getEmail());
	        if (updatedUserDto.getPhoneNumber() != null) user.setPhoneNumber(updatedUserDto.getPhoneNumber());

	        userRepo.save(user);

	        responses.setStatusCode(200);
	        responses.setMessage("User profile updated successfully");
	    } catch (OurException e) {
	        responses.setStatusCode(404);
	        responses.setMessage(e.getMessage());
	    } catch (Exception e) {
	        responses.setStatusCode(500);
	        responses.setMessage("Error updating profile: " + e.getMessage());
	    }

	    return responses;
	}

}

	

