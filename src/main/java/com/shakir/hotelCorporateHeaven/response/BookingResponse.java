 package com.shakir.hotelCorporateHeaven.response;



import java.time.LocalDate;

import com.shakir.hotelCorporateHeaven.model.Rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
	private Long bookingId;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	private String guestFullName;

	private String email;
	
	private int numOfAdults;
	
	private int numOfChild;

	private int totalNumOfguests;
	
	private String bookingConfirmationId;
	
	private RoomResponse room;

	public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate,
			String bookingConfirmationId) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.bookingConfirmationId = bookingConfirmationId;
	}
	
}
