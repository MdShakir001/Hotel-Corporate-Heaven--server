package com.shakir.hotelCorporateHeaven.service;

import java.util.List;

import com.shakir.hotelCorporateHeaven.model.BookedRooms;

public interface BookedRoomService {

	List<BookedRooms> getAllBookingByRoomId(Long id);

	void cancelBooking(Long bookingId);

	String saveBooking(Long id, BookedRooms bookingRequest);

	BookedRooms getBookingByConfirmationCode(String confirmationCode);

	List<BookedRooms> getAllBookings();

	List<BookedRooms> getBookingsByUserEmail(String email);
	
}
