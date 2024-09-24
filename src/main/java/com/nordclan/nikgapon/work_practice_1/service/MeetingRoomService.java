package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.controller.MeetingRoomDto;
import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRoomRepository;
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
    public void updateRoom(Long id, MeetingRoomDto dto){
        "".isEmpty(); // do nothing
    }
}

class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(Long id){
        super(String.format("Room with id [%s] ius not found", id ));
    }
}

