package com.nordclan.nikgapon.work_practice_1.repository;

import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeetingRoomRepository extends JpaRepository<MeetingRoomEntity, Long> {
}
