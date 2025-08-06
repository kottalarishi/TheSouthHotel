package com.rishi.TheSouthHotel.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rishi.TheSouthHotel.entity.Room;

public interface RoomRepo  extends JpaRepository<Room, Long>{
	@Query("SELECT DISTINCT r.roomType FROM Room r")
	List<String> findDistinctRoomTypes();
	
	@Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (" +
		       "SELECT bk.room.id FROM Booking bk WHERE " +
		       "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")

	 List<Room> findAvailableRoomsByDatesAndTypes(
		        @Param("checkInDate") LocalDate checkIndate,
		        @Param("checkOutDate") LocalDate checkoutDate,
		        @Param("roomType") String roomTypes);
	
	@Query("SELECT r FROM Room r")
	List<Room> getAvailableRooms();
	
	
}
