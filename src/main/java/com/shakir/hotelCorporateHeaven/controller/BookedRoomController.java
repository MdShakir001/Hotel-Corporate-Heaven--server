package com.shakir.hotelCorporateHeaven.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shakir.hotelCorporateHeaven.exception.InvalidBookingRequestException;
import com.shakir.hotelCorporateHeaven.exception.ResourceNotFoundException;
import com.shakir.hotelCorporateHeaven.model.BookedRooms;
import com.shakir.hotelCorporateHeaven.model.Rooms;
import com.shakir.hotelCorporateHeaven.response.BookingResponse;
import com.shakir.hotelCorporateHeaven.response.RoomResponse;
import com.shakir.hotelCorporateHeaven.service.BookedRoomService;
import com.shakir.hotelCorporateHeaven.service.RoomService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
	private final BookedRoomService bookingService;
	private final RoomService roomService;
	@GetMapping("/allBookings")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<BookingResponse>> getAllBookings(){
		List<BookedRooms> bookings=bookingService.getAllBookings();
		List<BookingResponse> bookingResponses=new ArrayList();
		for(BookedRooms booking:bookings) {
			BookingResponse bookingResponse=getBookingResponse(booking);
			bookingResponses.add(bookingResponse);
			
		}
		return ResponseEntity.ok(bookingResponses);
	}
	@GetMapping("/confirmation/{confirmationCode}")
	public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
		try {
			BookedRooms booking=bookingService.getBookingByConfirmationCode(confirmationCode);
			BookingResponse bookingResponse=getBookingResponse(booking);
			return ResponseEntity.ok(bookingResponse);
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			
		}
	}
	@GetMapping("/user/{email}/bookings")
	public ResponseEntity<List<BookingResponse>> getBookingByUserEmail(@PathVariable String email){
		List<BookedRooms> bookings=bookingService.getBookingsByUserEmail(email);
		List<BookingResponse> bookingResponses=new ArrayList();
		for(BookedRooms booking:bookings) {
			BookingResponse bookingResponse=getBookingResponse(booking);
			bookingResponses.add(bookingResponse);
			
		}
		return ResponseEntity.ok(bookingResponses);
	}
	@PostMapping("/room/{id}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable Long id,
			@RequestBody BookedRooms bookingRequest){
		try {
			String confirmationCode =bookingService.saveBooking(id,bookingRequest);
			return ResponseEntity.ok("Room Booked Succesfully Your Booking Confirmation Code is :"+confirmationCode);
		}catch(InvalidBookingRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	@DeleteMapping("/booking/{bookingId}/delete")
	public void cancelBooking(@PathVariable Long bookingId) {
		bookingService.cancelBooking(bookingId);
	}
	private BookingResponse getBookingResponse(BookedRooms booking) {
		Rooms theRoom=roomService.getRoomById(booking.getRoom().getId()).get();
		RoomResponse room=new RoomResponse(theRoom.getId(),theRoom.getRoomType(),theRoom.getPrice());
		return new BookingResponse(booking.getBookingId(),booking.getCheckInDate(),
				booking.getCheckOutDate(),booking.getGuestFullName(),
				booking.getEmail(),booking.getNumOfAdults(),
				booking.getNumOfChild(),booking.getTotalNumOfguests(),
				booking.getBookingConfirmationId(),room
				);
		
	}
	

}
