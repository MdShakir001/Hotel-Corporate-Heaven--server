package com.shakir.hotelCorporateHeaven.service;
import java.io.IOException;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.rowset.serial.SerialBlob;

import com.shakir.hotelCorporateHeaven.exception.InternalServerException;
import com.shakir.hotelCorporateHeaven.exception.ResourceNotFoundException;
import com.shakir.hotelCorporateHeaven.model.Rooms;
import com.shakir.hotelCorporateHeaven.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepository roomRepository;
	@Override
	public Rooms addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws  SQLException,  IOException {
		Rooms room=new Rooms();
		room.setRoomType(roomType);
		room.setPrice(roomPrice);
		if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
	}
	@Override
	public List<String> getAllRoomTypes() {
		
		return roomRepository.findDistinctRoomTypes();
	}
	
	
	@Override
	public List<Rooms> getAllRooms() {
		return roomRepository.findAll();
	}
	
	@Override
	public byte[] getRoomPhotoById(Long id) throws SQLException {
		Optional<Rooms> room=roomRepository.findById(id);
		if(room==null) {
			throw new ResourceNotFoundException("Sorry ,Room not found");
		}
		Blob blob=room.get().getPhoto();
		if(blob!=null) {
			return blob.getBytes(1, (int)blob.length());
		}
		
		return null;
	}
	@Override
	public void deleteRoomById(Long id) {
		Optional<Rooms> room=roomRepository.findById(id);
		if(room.isPresent()) {
			roomRepository.deleteById(id);
		}
		
		
	}
	
	@Override
	public Rooms updateRoom(Long id,String roomType, BigDecimal roomPrice, byte[] photoBytes) throws Exception {
		Rooms room=roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sorry,Room not Found"));
		if(roomType!=null) room.setRoomType(roomType);
		if(roomPrice!=null) room.setPrice(roomPrice);
		if(photoBytes!=null && photoBytes.length>0) {
			try{
				room.setPhoto(new SerialBlob(photoBytes));
			}catch(SQLException ex) {
				throw new InternalServerException("Error Updating Room");
			}
		}
		
		
		
		return roomRepository.save(room);
	}
	@Override
	public Optional<Rooms> getRoomById(Long id) {
		
		return roomRepository.findById(id);
	}
	@Override
	public List<Rooms> getAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		
		return roomRepository.findAvailableRoomsByDateAndType(checkInDate,checkOutDate,roomType);
	}
	

}
