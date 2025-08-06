package com.rishi.TheSouthHotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rishi.TheSouthHotel.dto.Responses;
import com.rishi.TheSouthHotel.dto.RoomDto;
import com.rishi.TheSouthHotel.entity.Room;
import com.rishi.TheSouthHotel.exception.OurException;
import com.rishi.TheSouthHotel.repo.RoomRepo;
import com.rishi.TheSouthHotel.service.AwsS3Service;
import com.rishi.TheSouthHotel.service.interfac.IRoomService;
import com.rishi.TheSouthHotel.utils.Utils;

@Service
public class RoomService implements IRoomService {

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public Responses addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {

		Responses responses = new Responses();

		try {

			String image = awsS3Service.saveImageToS3(photo);
			Room room = new Room();
			room.setRoomUrl(image);
			room.setRoomType(roomType);
			room.setRoomPrice(roomPrice);
			room.setRoomDescription(description);
			Room savedRoom = roomRepo.save(room);

			RoomDto roomDto = Utils.mapRoomEntityToRoomDto(savedRoom);
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoom(roomDto);

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error in saving room" + e.getMessage());
		}

		return responses;
	}

	@Override
	public List<String> getAllRoomTypes() {

		return roomRepo.findDistinctRoomTypes();

	}

	@Override
	public Responses getAllRooms() {

		Responses responses = new Responses();

		try {
			List<Room> rooms = roomRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<RoomDto> roomDtos = Utils.mapRoomListEntityToRoomListDto(rooms);
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoomDtos(roomDtos);

		} catch (Exception e) {
			responses.setStatusCode(500);
			responses.setMessage("error in saving room" + e.getMessage());
		}
		return responses;
	}

	@Override
	public Responses deleteRoom(Long roomId) {
		Responses responses = new Responses();

		try {

			roomRepo.findById(roomId).orElseThrow(() -> new OurException("room not found"));
			roomRepo.deleteById(roomId);
			responses.setStatusCode(200);
			responses.setMessage("successful");
		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error in deleting room" + e.getMessage());

		}

		return responses;
	}

	@Override
	public Responses upadateRoom(Long roomId, String roomType, String descrption, BigDecimal roomPrice,
			MultipartFile photo) {

		Responses responses = new Responses();

		try {

			String image = null;
			if (photo != null && !photo.isEmpty()) {
				image = awsS3Service.saveImageToS3(photo);
			}

			Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("room not found"));
			if (roomType != null)
				room.setRoomType(roomType);
			if (roomPrice != null)
				room.setRoomPrice(roomPrice);
			if (descrption != null)
				room.setRoomDescription(descrption);
			if (image != null)
				room.setRoomUrl(image);

			Room updatedRoom = roomRepo.save(room);
			RoomDto roomDto = Utils.mapRoomEntityToRoomDto(updatedRoom);

			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoom(roomDto);

		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error in updating room" + e.getMessage());

		}

		return responses;
	}

	@Override
	public Responses getRoomByid(Long roomId) {
		Responses responses = new Responses();

		try {

			Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("room not found"));
			RoomDto  roomDto= Utils.mapRoomEntityToRoomDTOPLusBookings(room);
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoom(roomDto);
		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error in deleting room" + e.getMessage());

		}

		return responses;
	}

	@Override
	public Responses getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		
		Responses responses = new Responses();

		try {
			List<Room> availableRooms= roomRepo.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
		List<RoomDto> roomDtos= Utils.mapRoomListEntityToRoomListDto(availableRooms);
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoomDtos(roomDtos);
		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error finding rooms" + e.getMessage());

		}

		return responses;
	}

	@Override
	public Responses getAllAvailableRooms() {
		Responses responses = new Responses();
		
		try {
			List<Room> rooms = roomRepo.getAvailableRooms();
			List<RoomDto> roomDtoList= Utils.mapRoomListEntityToRoomListDto(rooms);
			
			
			responses.setStatusCode(200);
			responses.setMessage("successful");
			responses.setRoomDtos(roomDtoList);;
		} catch (OurException e) {

			responses.setStatusCode(404);
			responses.setMessage(e.getMessage());

		} catch (Exception e) {

			responses.setStatusCode(500);
			responses.setMessage("error in deleting room" + e.getMessage());

		}

		return responses;

	}

}
