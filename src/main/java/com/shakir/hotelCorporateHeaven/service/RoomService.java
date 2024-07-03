package com.shakir.hotelCorporateHeaven.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.shakir.hotelCorporateHeaven.model.Rooms;

public interface RoomService {

	Rooms addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws  SQLException, IOException;

	List<String> getAllRoomTypes();

	List<Rooms> getAllRooms();

	byte[] getRoomPhotoById(Long id) throws SQLException;

	void deleteRoomById(Long id);

	Rooms updateRoom(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes) throws Exception;

	Optional<Rooms> getRoomById(Long id);

	List<Rooms> getAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

	
	
}
