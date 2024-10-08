package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.controller.MeetingDto;
import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {
    @Autowired
    private final MeetingRepository meetingRepository;
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }


    @Transactional(readOnly = true)
    public List<MeetingEntity> findByTimeInterval(LocalDateTime starttime, LocalDateTime endtime) {
        return meetingRepository.findAllByTimeInterval(starttime, endtime);
    }

    @Transactional(readOnly = true)
    public List<MeetingEntity> findByTimeInterval(LocalDateTime starttime, LocalDateTime endtime, Long id) {
        return meetingRepository.findByTimeInterval(starttime, endtime, id);
    }

    @Transactional
    public MeetingEntity findMeeting(Long id){
        final Optional<MeetingEntity> meeting = meetingRepository.findById(id);
        return meeting.orElseThrow(() -> new MeetingNotFoundException(id));

    }
    @Transactional
    public void deleteMeeting(Long id){
        meetingRepository.deleteById(id);
    }

    @Transactional
    public MeetingEntity addRoom(MeetingDto dto) {
        final MeetingEntity meeting = new MeetingEntity(dto);
        return meetingRepository.save(meeting);
    }
    @Transactional
    public boolean isRoomOccupied(MeetingRoomEntity room, Timestamp startTime, Timestamp endTime, Long id) {
        List<MeetingEntity> existingMeetings = meetingRepository.findByRoomAndTimeRange(room, startTime, endTime, id);

        return !existingMeetings.isEmpty();
    }

    @Transactional
    public MeetingEntity updateRoom(Long id, MeetingDto dto) {
        final MeetingEntity meeting = findMeeting(id);
        meeting.setTitle(dto.getTitle());
        meeting.setDescription(dto.getDescription());
        meeting.setRoom(dto.getRoom());
        meeting.setCreator(dto.getCreator());
        meeting.setStarttime(dto.getStarttime());
        meeting.setEndtime(dto.getEndtime());
        meeting.setGuests(dto.getGuests());
        return meetingRepository.save(meeting);

    }

    @Transactional
    public List<MeetingEntity> findAllInvitations(Long id){
        return meetingRepository.findAllMeetingsByGuestUserId(id);
    }
}

class MeetingNotFoundException extends RuntimeException{
    public MeetingNotFoundException(Long id){
        super(String.format("Meeting with id [%s] ius not found", id ));
    }
}
