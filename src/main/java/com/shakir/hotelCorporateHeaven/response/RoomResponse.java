package com.shakir.hotelCorporateHeaven.response;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class RoomResponse {
	
	private Long id;

	private String roomType;
	
	private BigDecimal price;

	private boolean isBooked;

	private String photo;
	private List<BookingResponse> bookingList ;
	public RoomResponse(Long id, String roomType, BigDecimal price) {
		this.id = id;
		this.roomType = roomType;
		this.price = price;
	}
	public RoomResponse(Long id, String roomType, BigDecimal price,
			boolean isBooked, byte[] photoBytes) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.price = price;
		this.isBooked = isBooked;
		this.photo = photoBytes !=null ?Base64.encodeBase64String(photoBytes):null ;
		//this.bookingList = bookingList;
	}
}
