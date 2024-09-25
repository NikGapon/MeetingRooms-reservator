package com.nordclan.nikgapon.work_practice_1.repository;

import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface MeetingRepository extends JpaRepository<MeetingRoomEntity, Long> {
    @Query(value = "SELECT * FROM public.meeting WHERE starttime > ?1 OR endtime <= ?2",nativeQuery = true)
    List<MeetingRoomEntity> findByTimeInterval(LocalDate startDate, LocalDate endDate);
}
