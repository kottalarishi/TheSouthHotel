package com.rishi.TheSouthHotel.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

	private Long id;
	private String email;
	private String name;
	private String PhoneNumber;
	private String role;
	
	private List<BookingsDto> bookingsDtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<BookingsDto> getBookingsDtos() {
		return bookingsDtos;
	}

	public void setBookingsDtos(List<BookingsDto> bookingsDtos) {
		this.bookingsDtos = bookingsDtos;
	}
	
	
	
}
