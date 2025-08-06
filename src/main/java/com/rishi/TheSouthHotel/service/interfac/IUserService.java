package com.rishi.TheSouthHotel.service.interfac;

import com.rishi.TheSouthHotel.dto.LoginRequest;
import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.dto.UserDto;
import com.rishi.TheSouthHotel.entity.User;

public interface IUserService {

	
		Responses register(User loginRequest);
		
		Responses login(LoginRequest loginRequest);
		Responses getAllUsers();
		
		Responses getUserbookingHistory(String userId);
		
		Responses deleteUser(String userID);
		
		Responses getUserById(String userId);
		
		Responses getMyInfo(String email);
		
		Responses updateUserProfile(Long userId, UserDto updatedUserDto);

}
