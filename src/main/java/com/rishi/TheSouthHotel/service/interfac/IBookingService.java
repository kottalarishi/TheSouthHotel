package com.rishi.TheSouthHotel.service.interfac;

import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.entity.Booking;

public interface IBookingService {

	
	Responses saveBooking(Long roomId,Long userId,Booking bookingRequest);
	
	Responses findBookingByConfirmationCode(String confirmationCode);
	
	Responses getAllBookings();
	
	Responses cancelBooking(Long bookingId);
}
