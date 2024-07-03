package com.shakir.hotelCorporateHeaven.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shakir.hotelCorporateHeaven.model.Rooms;

public interface RoomRepository extends JpaRepository<Rooms,Long> {
	@Query("SELECT DISTINCT r.roomType FROM Rooms r")
	List<String> findDistinctRoomTypes();
	@Query("SELECT r FROM Rooms r "+
	"WHERE r.roomType LIKE %:roomType% "+
			"AND r.id NOT IN ("+
	"SELECT br.room.id FROM BookedRooms br "+
			" WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))"+
	")")
	List<Rooms> findAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
