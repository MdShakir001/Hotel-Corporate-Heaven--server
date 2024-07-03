package com.shakir.hotelCorporateHeaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shakir.hotelCorporateHeaven.model.BookedRooms;

public interface BookedRoomRepository extends JpaRepository<BookedRooms,Long>  {

	List<BookedRooms> findByRoomId(Long id);
	Optional<BookedRooms> findByBookingConfirmationId(String confirmationCode);
	List<BookedRooms> findByEmail(String email);

}
