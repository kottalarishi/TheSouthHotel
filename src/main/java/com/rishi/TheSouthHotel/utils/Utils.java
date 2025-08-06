package com.rishi.TheSouthHotel.utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.rishi.TheSouthHotel.dto.BookingsDto;
import com.rishi.TheSouthHotel.dto.RoomDto;
import com.rishi.TheSouthHotel.dto.UserDto;
import com.rishi.TheSouthHotel.entity.Booking;
import com.rishi.TheSouthHotel.entity.Room;
import com.rishi.TheSouthHotel.entity.User;

public class Utils {
	
	    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    private static final SecureRandom secureRandom = new SecureRandom();

	    public static String generateRandomConfirmationCode(int length) {
	        StringBuilder stringBuilder = new StringBuilder();
	        for (int i = 0; i < length; i++) {
	            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
	            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
	            stringBuilder.append(randomChar);
	        }
	        return stringBuilder.toString();
	    
	}
	    
	    public static RoomDto mapRoomEntityToRoomDto(Room room) {
	    	
	    	RoomDto roomDto= new RoomDto();
	    	roomDto.setRoomId(room.getId());
	    	roomDto.setRoomPrice(room.getRoomPrice());
	    	roomDto.setRoomType(room.getRoomType());
	    	roomDto.setRoomUrl(room.getRoomUrl());
	    	roomDto.setRoomDescription(room.getRoomDescription());
	    	return roomDto;
	    }
	    
	    public static UserDto mapUserEntityToUserDto(User user) {
	    	UserDto userDto= new UserDto();
	    	
	    	userDto.setId(user.getId());
	    	userDto.setName(user.getName());
	    	userDto.setEmail(user.getEmail());
	    	userDto.setPhoneNumber(user.getPhoneNumber());
	    	userDto.setRole(user.getRole());
	    	
	    	return userDto;
	    }
	    
	    public static BookingsDto mapBookingEntityToBookingDto(Booking booking) {
	    	
	    	BookingsDto bookingsDto= new BookingsDto();
	    	
	    	bookingsDto.setId(booking.getId());
	    	bookingsDto.setNoOfAdults(booking.getNoOfAdults());
	    	bookingsDto.setNoOfChildren(booking.getNoOfChildren());
	    	bookingsDto.setTotalNoOfGuest(booking.getTotalNoOfGuest());
	    	bookingsDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
	    	bookingsDto.setCheckInDate(booking.getCheckInDate());
	    	bookingsDto.setCheckOutDate(booking.getCheckOutDate());
	    	
	    	return bookingsDto;
	    	
	    	
	    }
	    	
	    public static RoomDto mapRoomEntityToRoomDTOPLusBookings(Room room) {
	        RoomDto roomDto = new RoomDto();

	        roomDto.setRoomId(room.getId());
	        roomDto.setRoomType(room.getRoomType());
	        roomDto.setRoomPrice(room.getRoomPrice());
	        roomDto.setRoomUrl(room.getRoomUrl());
	    	roomDto.setRoomDescription(room.getRoomDescription());


	        if (room.getBookings() != null) {
	            roomDto.setBookingsDtos(room.getBookings()
	                .stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList()));

	                
	        }

	        return roomDto;
	    }

	    public static UserDto mapUserEntityToUserDTOPLusUserBookingsAndRoom(User user) {
	        UserDto userDto = new UserDto();

	        userDto.setId(user.getId());
	        userDto.setName(user.getName());
	        userDto.setEmail(user.getEmail());
	        userDto.setPhoneNumber(user.getPhoneNumber());
	        userDto.setRole(user.getRole());

	        if (!user.getBookings().isEmpty()) {
	        	userDto.setBookingsDtos(
	        		    user.getBookings().stream()
	        		        .map(booking -> Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, false))
	        		        .collect(Collectors.toList())
	        		);
	        }

	        return userDto;
	    }
	    
	    public static BookingsDto mapBookingEntityToBookingDTOPlusBookedRooms(Booking booking, boolean mapUser) {
	        BookingsDto bookingsDto = new BookingsDto();
	        
	        bookingsDto.setId(booking.getId());
	        bookingsDto.setCheckInDate(booking.getCheckInDate());
	        bookingsDto.setCheckOutDate(booking.getCheckOutDate());
	        bookingsDto.setNoOfAdults(booking.getNoOfAdults());
	        bookingsDto.setNoOfChildren(booking.getNoOfChildren());
	        bookingsDto.setTotalNoOfGuest(booking.getTotalNoOfGuest());
	        bookingsDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
	        
	        if (mapUser && booking.getUser() != null) {
	            bookingsDto.setUserDto(Utils.mapUserEntityToUserDto(booking.getUser()));
	        }

	        if (booking.getRoom() != null) {
	            RoomDto roomDTO = new RoomDto();
	            roomDTO.setRoomId(booking.getRoom().getId());
	            roomDTO.setRoomType(booking.getRoom().getRoomType());
	            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
	            roomDTO.setRoomUrl(booking.getRoom().getRoomUrl());
	            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
	            bookingsDto.setRoomDto(roomDTO);
	        }

	        
	        return bookingsDto;
	    }
	    
	    public static List<UserDto> mapUserListEntityToUserListDto(List<User> users){
	    	return users.stream().map(Utils::mapUserEntityToUserDto).collect(Collectors.toList());
	    }
	    public static List<RoomDto> mapRoomListEntityToRoomListDto(List<Room> rooms){
	    	return rooms.stream().map(Utils::mapRoomEntityToRoomDto).collect(Collectors.toList());
	    }
	    
	    
	    public static List<BookingsDto> mapBookingListEntityToBookingListDtoWithRoomAndUser(List<Booking> bookings) {
	        return bookings.stream().map(booking -> {
	            BookingsDto dto = new BookingsDto();
	            dto.setId(booking.getId());
	            dto.setCheckInDate(booking.getCheckInDate());
	            dto.setCheckOutDate(booking.getCheckOutDate());
	            dto.setNoOfAdults(booking.getNoOfAdults());
	            dto.setNoOfChildren(booking.getNoOfChildren());
	            dto.setTotalNoOfGuest(booking.getTotalNoOfGuest());
	            dto.setBookingConfirmationCode(booking.getBookingConfirmationCode());

	            // Set Room Details
	            Room room = booking.getRoom();
	            if (room != null) {
	                RoomDto roomDto = new RoomDto();
	                roomDto.setRoomId(room.getId());
	                roomDto.setRoomType(room.getRoomType());
	                roomDto.setRoomPrice(room.getRoomPrice());
	                roomDto.setRoomUrl(room.getRoomUrl());
	                roomDto.setRoomDescription(room.getRoomDescription());
	                dto.setRoomDto(roomDto);
	            }

	            // Set User Details
	            User user = booking.getUser();
	            if (user != null) {
	                UserDto userDto = new UserDto();
	                userDto.setId(user.getId());
	                userDto.setName(user.getName());
	                userDto.setEmail(user.getEmail());
	                userDto.setPhoneNumber(user.getPhoneNumber());
	                userDto.setRole(user.getRole());
	                dto.setUserDto(userDto);
	            }

	            return dto;
	        }).collect(Collectors.toList());
	    }




}
