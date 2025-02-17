package com.shakir.hotelCorporateHeaven.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Rooms {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String roomType;
	
	private BigDecimal price;

	private boolean isBooked =false;
	@Lob
	private Blob photo;
	
	@OneToMany(mappedBy="room",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<BookedRooms> bookings;
	public Rooms() {
		this.bookings=new ArrayList<>();
	}
	public void addBooking(BookedRooms booking) {
		if(bookings==null) {
			bookings=new ArrayList();
		}
		bookings.add(booking);
		booking.setRoom(this);
		isBooked=true;
		String bookingCode =RandomStringUtils.randomNumeric(10);
		booking.setBookingConfirmationId(bookingCode);
	}
}
