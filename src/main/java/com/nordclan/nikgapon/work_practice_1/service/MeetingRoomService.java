package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
