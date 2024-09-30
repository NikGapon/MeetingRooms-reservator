package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRepository;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        return meetingRepository.findByTimeInterval(starttime, endtime);
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
}

class MeetingNotFoundException extends RuntimeException{
    public MeetingNotFoundException(Long id){
        super(String.format("Meeting with id [%s] ius not found", id ));
    }
}
