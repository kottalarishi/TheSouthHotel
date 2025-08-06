package com.rishi.TheSouthHotel.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Responses {
	
	private int statusCode;
	private String message;
	private String token;
	private String role;
	private String expirationTime;
	private String bookingConfirmationCode;
	
	private UserDto user;
	private RoomDto room;
	private BookingsDto bookings;
	private List<UserDto> userDtos;
	private List<RoomDto> roomDtos;
	private List<BookingsDto> bookingsDtos;
	private List<BookingsDto> data;
	public List<BookingsDto> getData() {
		return data;
	}
	public void setData(List<BookingsDto> data) {
		this.data = data;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}
	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public RoomDto getRoom() {
		return room;
	}
	public void setRoom(RoomDto room) {
		this.room = room;
	}
	public BookingsDto getBookings() {
		return bookings;
	}
	public void setBookings(BookingsDto bookings) {
		this.bookings = bookings;
	}
	public List<UserDto> getUserDtos() {
		return userDtos;
	}
	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}
	public List<RoomDto> getRoomDtos() {
		return roomDtos;
	}
	public void setRoomDtos(List<RoomDto> roomDtos) {
		this.roomDtos = roomDtos;
	}
	public List<BookingsDto> getBookingsDtos() {
		return bookingsDtos;
	}
	public void setBookingsDtos(List<BookingsDto> bookingsDtos) {
		this.bookingsDtos = bookingsDtos;
	}
	
	
	 
}
