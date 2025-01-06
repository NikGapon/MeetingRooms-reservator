package com.nikgapon.MeetingRoomsReservator.repository;

import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoomEntity, Long> {
}
