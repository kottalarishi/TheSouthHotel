package com.rishi.TheSouthHotel.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishi.TheSouthHotel.entity.Booking;

public interface BookingRepo  extends JpaRepository<Booking, Long>{
	
	List<Booking> findByroom_Id(Long roomId);
	Optional<Booking> findByBookingConfirmationCode(String ConfirmationCode);
	List<Booking> findByUserId(Long userId);
}
