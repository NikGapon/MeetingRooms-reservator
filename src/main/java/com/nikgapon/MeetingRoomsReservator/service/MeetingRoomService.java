package com.nikgapon.MeetingRoomsReservator.service;

import com.nikgapon.MeetingRoomsReservator.dto.MeetingRoomDto;
import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import com.nikgapon.MeetingRoomsReservator.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingRoomService {

    @Autowired
    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Transactional(readOnly = true)
    public List<MeetingRoomEntity> findAllRooms() {
        return meetingRoomRepository.findAll();
    }

    @Transactional
    public void deleteRoom(Long id){
        meetingRoomRepository.deleteById(id);
    }

    @Transactional
    public MeetingRoomEntity findRoom(Long id){
        final Optional<MeetingRoomEntity> room = meetingRoomRepository.findById(id);
        return room.orElseThrow(() -> new RoomNotFoundException(id));

    }

    @Transactional
    public MeetingRoomEntity addRoom(MeetingRoomDto dto){
        final MeetingRoomEntity room = new MeetingRoomEntity(dto);
        return meetingRoomRepository.save(room);
    }

    @Transactional
    public MeetingRoomEntity updateRoom(Long id, MeetingRoomDto dto){
        final MeetingRoomEntity room = findRoom(id);
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        return meetingRoomRepository.save(room);
    }

}

class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(Long id){
        super(String.format("Room with id [%s] ius not found", id ));
    }
}

