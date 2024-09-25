package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRepository;
import com.nordclan.nikgapon.work_practice_1.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }
    @Transactional(readOnly = true)
    public List<MeetingRoomEntity> findByTimeInterval(LocalDate starttime, LocalDate endtime) {
        return meetingRepository.findByTimeInterval(starttime, endtime);
    }
}
