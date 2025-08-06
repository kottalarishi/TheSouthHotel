package com.rishi.TheSouthHotel.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto {
	
private Long roomId;
	
	private String roomType;
	private BigDecimal roomPrice;
	private String roomUrl;
	private String roomDescription;
	
	List<BookingsDto> bookingsDtos;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getRoomUrl() {
		return roomUrl;
	}

	public void setRoomUrl(String roomUrl) {
		this.roomUrl = roomUrl;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public List<BookingsDto> getBookingsDtos() {
		return bookingsDtos;
	}

	public void setBookingsDtos(List<BookingsDto> bookingsDtos) {
		this.bookingsDtos = bookingsDtos;
	}
	
	
}
