package com.rishi.TheSouthHotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.TheSouthHotel.dto.BookingsDto;
import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.entity.Booking;
import com.rishi.TheSouthHotel.entity.Room;
import com.rishi.TheSouthHotel.entity.User;
import com.rishi.TheSouthHotel.exception.OurException;
import com.rishi.TheSouthHotel.repo.BookingRepo;
import com.rishi.TheSouthHotel.repo.RoomRepo;
import com.rishi.TheSouthHotel.repo.UserRepo;
import com.rishi.TheSouthHotel.service.interfac.IBookingService;
import com.rishi.TheSouthHotel.utils.Utils;

import jakarta.transaction.Transactional;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	public Responses saveBooking(Long roomId, Long userId, Booking bookingRequest) {

		Responses responses = new Responses();

		try {

			if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("checkin date must before checkout date");
			}

			Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("room not found"));

			User user = userRepo.findById(userId).orElseThrow(() -> new OurException("user not found"));

			List<Booking> existBookings = room.getBookings();
			if (!roomIsAvailable(bookingRequest, existBookings)) {
				throw new OurException("rooms not available for selected date range");
			}

			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			bookingRequest.calculateTotalNumberOfGuest();
			
			user.getBookings().add(bookingRequest);
			room.getBookings().add(bookingRequest);

			
			String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			bookingRepo.save(bookingRequest);
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setBookingConfirmationCode(bookingConfirmationCode);

		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error while saving a booking" + e.getMessage());

		}

		return responses;
	}

	@Override
	public Responses findBookingByConfirmationCode(String confirmationCode) {
		Responses responses = new Responses();

		try {

			Booking booking = bookingRepo.findByBookingConfirmationCode(confirmationCode)
					.orElseThrow(() -> new OurException("booking not found"));
			BookingsDto bookingsDto = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking,true );

			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setBookings(bookingsDto);

		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error while fetching a booking" + e.getMessage());

		}

		return responses;
	}
	@Override
	public Responses getAllBookings() {
	    Responses responses = new Responses();

	    try {
	        List<Booking> bookings = bookingRepo.findAll();

	        if (bookings.isEmpty()) {
	            responses.setStatusCode(200);
	            responses.setMessage("No bookings found");
	            responses.setData(List.of());  // empty list
	            return responses;
	        }

	        // ✅ Use the correct mapping method
	        List<BookingsDto> bookingsDtos = Utils.mapBookingListEntityToBookingListDtoWithRoomAndUser(bookings);

	        responses.setStatusCode(200);
	        responses.setMessage("Bookings retrieved successfully");
	        responses.setData(bookingsDtos);

	    } catch (Exception e) {
	        responses.setStatusCode(500);
	        responses.setMessage("Failed to retrieve bookings: " + e.getMessage());
	    }

	    return responses;
	}


	@Transactional
	@Override
	public Responses cancelBooking(Long bookingId) {
	    Responses responses = new Responses();

	    try {
	        Booking booking = bookingRepo.findById(bookingId)
	                .orElseThrow(() -> new OurException("Booking does not exist"));

	        // Break references (IMPORTANT!)
	        User user = booking.getUser();
	        Room room = booking.getRoom();

	        if (user != null) {
	            user.getBookings().remove(booking);
	            booking.setUser(null);
	        }

	        if (room != null) {
	            room.getBookings().remove(booking);
	            booking.setRoom(null);
	        }

	        bookingRepo.delete(booking);
	        bookingRepo.flush();  // or use bookingRepo.flush(); if needed

	        responses.setStatusCode(200);
	        responses.setMessage("Booking deleted successfully");

	    } catch (OurException e) {
	        responses.setStatusCode(404);
	        responses.setMessage(e.getMessage());
	    } catch (Exception e) {
	        responses.setStatusCode(500);
	        responses.setMessage("Error while deleting booking: " + e.getMessage());
	    }

	    return responses;
	}

	
	private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()));
	}

}
