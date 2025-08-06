package com.rishi.TheSouthHotel.service.interfac;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rishi.TheSouthHotel.dto.Responses;

public interface IRoomService {
	
	Responses addNewRoom(MultipartFile photo,String roomType,BigDecimal roomPrice,String description);
	
	Responses getAllRooms();
	
	List<String> getAllRoomTypes();
	
	Responses deleteRoom(Long roomId);
	
	Responses upadateRoom(Long roomId,String roomType, String descrption,BigDecimal roomPrice, MultipartFile photo);
	
	Responses getRoomByid(Long roomId);
	
	Responses getAvailableRoomsByDateAndType(LocalDate checkInDate,LocalDate checkOutDate,String roomType);
	
	Responses getAllAvailableRooms();
	

}
