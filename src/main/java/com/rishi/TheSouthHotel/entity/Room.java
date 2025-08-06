package com.rishi.TheSouthHotel.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private BigDecimal roomPrice;
    private String roomUrl;
    private String roomDescription;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    
    
    

    public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
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





	public List<Booking> getBookings() {
		return bookings;
	}





	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}





	@Override
    public String toString() {
        return "Room [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice +
                ", roomUrl=" + roomUrl + ", roomDescription=" + roomDescription + "]";
    }
}
