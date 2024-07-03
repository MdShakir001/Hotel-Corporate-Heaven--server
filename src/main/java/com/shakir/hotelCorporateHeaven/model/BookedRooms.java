package com.shakir.hotelCorporateHeaven.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookedRooms {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookingId;
	@Column(name="check_In")
	private LocalDate checkInDate;
	@Column(name="check_Out")
	private LocalDate checkOutDate;
	@Column(name="guest_FullName")
	private String guestFullName;
	@Column(name="guest_email")
	private String email;
	@Column(name="childs")
	private int numOfChild;
	@Column(name="adults")
	private int numOfAdults;
	@Column(name="total_guests")
	private int totalNumOfguests;
	@Column(name="confirmation_id")
	private String bookingConfirmationId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roo_id")
	private Rooms room;
	
	
	
	
	public void calculateTotalNumberOfGuests() {
		this.totalNumOfguests=this.numOfAdults+this.numOfChild;
	}
	public void setRoom(Rooms room) {
		this.room=room;
	}
	public int getNumOfChild() {
		return numOfChild;
	}
	public void setNumOfChild(int numOfChild) {
		this.numOfChild = numOfChild;
		calculateTotalNumberOfGuests();
	}
	public int getNumOfAdults() {
		return numOfAdults;
	}
	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults = numOfAdults;
		calculateTotalNumberOfGuests();
	}
	public String getBookingConfirmationId() {
		return bookingConfirmationId;
	}
	public void setBookingConfirmationId(String bookingConfirmationId) {
		this.bookingConfirmationId = bookingConfirmationId;
	}
	
	
	
	

}
