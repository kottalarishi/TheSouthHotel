package com.rishi.TheSouthHotel.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingsDto {
	private Long id;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private int noOfAdults;
	private int noOfChildren;
	private int totalNoOfGuest;	
	private String bookingConfirmationCode;
	private UserDto userDto;
	
	private RoomDto roomDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getNoOfAdults() {
		return noOfAdults;
	}

	public void setNoOfAdults(int noOfAdults) {
		this.noOfAdults = noOfAdults;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public int getTotalNoOfGuest() {
		return totalNoOfGuest;
	}

	public void setTotalNoOfGuest(int totalNoOfGuest) {
		this.totalNoOfGuest = totalNoOfGuest;
	}

	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public RoomDto getRoomDto() {
		return roomDto;
	}

	public void setRoomDto(RoomDto roomDto) {
		this.roomDto = roomDto;
	}
	
	
}
