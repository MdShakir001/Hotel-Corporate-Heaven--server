package com.shakir.hotelCorporateHeaven.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.shakir.hotelCorporateHeaven.exception.InvalidBookingRequestException;
import com.shakir.hotelCorporateHeaven.exception.ResourceNotFoundException;
import com.shakir.hotelCorporateHeaven.model.BookedRooms;
import com.shakir.hotelCorporateHeaven.model.Rooms;
import com.shakir.hotelCorporateHeaven.repository.BookedRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl implements BookedRoomService{
	private final BookedRoomRepository bookingRepository;
	private final RoomService roomService;

	@Override
	public List<BookedRooms> getAllBookingByRoomId(Long id) {
		
		return bookingRepository.findByRoomId(id);
	}

	@Override
	public void cancelBooking(Long bookingId) {
		
			bookingRepository.deleteById(bookingId);
		
		
	}

	@Override
	public String saveBooking(Long id, BookedRooms bookingRequest) {
		if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check In Date must be come before Check Out Date");
		}
		Rooms room= roomService.getRoomById(id).get();
		List<BookedRooms> existingBookings=room.getBookings();
		boolean roomIsAvailable=roomIsAvailable(bookingRequest,existingBookings);
		if(roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRepository.save(bookingRequest);
		}else {
			throw new InvalidBookingRequestException("Sorry this room is not available  for selected date ");
			
			
		}
		return bookingRequest.getBookingConfirmationId();
	}

	

	@Override
	public BookedRooms getBookingByConfirmationCode(String confirmationCode) {
		
		return bookingRepository.findByBookingConfirmationId(confirmationCode)
				.orElseThrow(()->new ResourceNotFoundException("No Booking Found with code :"+confirmationCode));
	}
	@Override
	public List<BookedRooms> getBookingsByUserEmail(String email) {
		
		return bookingRepository.findByEmail(email);
	}
	@Override
	public List<BookedRooms> getAllBookings() {
		
		return bookingRepository.findAll();
	}
    private boolean roomIsAvailable(BookedRooms bookingRequest, List<BookedRooms> existingBookings) {
    	return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
	}


	

}
