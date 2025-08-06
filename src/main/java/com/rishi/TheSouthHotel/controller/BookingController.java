	package com.rishi.TheSouthHotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.entity.Booking;
import com.rishi.TheSouthHotel.service.interfac.IBookingService;


@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@Autowired
	 private IBookingService iBookingService;
	
	@PostMapping("/bookRoom/{roomId}/{userId}")
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	public ResponseEntity<Responses> saveBookings(@PathVariable Long roomId,
												@PathVariable Long userId,
												@RequestBody Booking booking){
		
		Responses responses=iBookingService.saveBooking(roomId, userId, booking);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
		
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Responses> getAllBookings() {
	    Responses responses = iBookingService.getAllBookings();
	    return ResponseEntity
	            .status(responses.getStatusCode())
	            .body(responses);
	}

	
	@GetMapping("/getByconfirmationCode/{confirmationCode}")
	public ResponseEntity<Responses> getByConfirmationCode(@PathVariable String confirmationCode) {
		Responses responses = iBookingService.findBookingByConfirmationCode(confirmationCode);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	
	@DeleteMapping("/delete/{bookingId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Responses> cancleBooking(@PathVariable Long  bookingId) {
		Responses responses = iBookingService.cancelBooking(bookingId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
}
	
	
