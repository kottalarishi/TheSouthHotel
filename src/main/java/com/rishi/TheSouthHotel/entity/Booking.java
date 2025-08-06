package com.rishi.TheSouthHotel.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "noOfAdults should not be zero")
    private int noOfAdults;

    @Min(value = 0, message = "Children can be zero")
    private int noOfChildren;

    private int totalNoOfGuest;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")  // This creates room_id column in booking table
    private Room room;


    @PrePersist
    @PreUpdate
    public void calculateTotalNumberOfGuest() {
        this.totalNoOfGuest = this.noOfAdults + this.noOfChildren;
    }
    
    
    
    	

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





	public User getUser() {
		return user;
	}





	public void setUser(User user) {
		this.user = user;
	}





	public Room getRoom() {
		return room;
	}





	public void setRoom(Room room) {
		this.room = room;
	}





	@Override
    public String toString() {
        return "Booking [id=" + id + ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate + ", noOfAdults=" + noOfAdults +
                ", noOfChildren=" + noOfChildren + ", totalNoOfGuest=" + totalNoOfGuest +
                ", bookingConfirmationCode=" + bookingConfirmationCode + ", user=" + (user != null ? user.getId() : null) + "]";
    }
}
