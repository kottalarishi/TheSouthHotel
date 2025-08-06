package com.rishi.TheSouthHotel.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.service.interfac.IRoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private IRoomService iRoomService;
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Responses> addNewRoom(@RequestParam(value = "photo", required = false) MultipartFile photo,
			@RequestParam(value = "roomType", required = false) String roomType,
			@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
			@RequestParam(value = "roomDescription", required = false) String roomDescription) {

		if (photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null
				|| roomType.isBlank()) {
			Responses responses = new Responses();
			responses.setStatusCode(400);
			responses.setMessage("please provide vakues for all fields");
		}

		Responses responses = iRoomService.addNewRoom(photo, roomType, roomPrice, roomDescription);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<Responses> getAllRooms() {
		Responses responses = iRoomService.getAllRooms();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping("/types")
	public List<String> getRoomTypes() {
		return iRoomService.getAllRoomTypes();
	}
	
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/roomById/{roomId}")
	public ResponseEntity<Responses> getRoomById(@PathVariable Long roomId) {
		Responses responses = iRoomService.getRoomByid(roomId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping("/allAvailableRooms")
	public ResponseEntity<Responses> getAllAvailableRooms() {
		Responses responses = iRoomService.getAllAvailableRooms();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping("/availableRoomsBYDateAndType")
	public ResponseEntity<Responses> getAvailableRoomsBYDateAndType(
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
	    @RequestParam String roomType) {

	    if (checkInDate == null || checkOutDate == null || roomType == null || roomType.isBlank()) {
	        Responses responses = new Responses();
	        responses.setStatusCode(400);
	        responses.setMessage("Please provide values for all fields");
	        return ResponseEntity.status(responses.getStatusCode()).body(responses);
	    }

	    Responses responses = iRoomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
	    return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PutMapping("/update/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Responses> updateRoom(@PathVariable Long roomId,
			@RequestParam(value = "photo", required = false) MultipartFile photo,
			@RequestParam(value = "roomType", required = false) String roomType,
			@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
			@RequestParam(value = "roomDescription", required = false) String roomDescription) {

		Responses responses = iRoomService.upadateRoom(roomId, roomType, roomDescription, roomPrice, photo);

		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}

	@DeleteMapping("/delete/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Responses> deleteRoom(@PathVariable Long roomId) {
		Responses responses = iRoomService.deleteRoom(roomId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}

}
