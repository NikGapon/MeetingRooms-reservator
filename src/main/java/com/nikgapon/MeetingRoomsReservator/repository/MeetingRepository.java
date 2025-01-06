package com.nikgapon.MeetingRoomsReservator.repository;

import com.nikgapon.MeetingRoomsReservator.model.MeetingEntity;
import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    @Query(value = "SELECT * FROM public.meeting WHERE starttime > ?1 AND endtime < ?2",nativeQuery = true)
    List<MeetingEntity> findAllByTimeInterval(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM MeetingEntity m WHERE m.starttime > :starttime AND m.endtime < :endtime AND m.room.id = :room_id")
    List<MeetingEntity> findByTimeInterval(@Param("starttime") Timestamp startDate,
                                           @Param("endtime") Timestamp endDate,
                                           @Param("room_id") Long room_id);

    @Query("SELECT m FROM MeetingEntity m WHERE m.room = :meetingRoom AND m.id <> :meetingId AND (m.starttime < :endtime AND m.endtime > :starttime)")
    List<MeetingEntity> findByRoomAndTimeRange(@Param("meetingRoom") MeetingRoomEntity meetingRoom,
                                               @Param("starttime") Timestamp starttime,
                                               @Param("endtime") Timestamp endtime,
                                               @Param("meetingId") Long meetingId);

    @Query("SELECT m FROM MeetingEntity m JOIN m.guests g WHERE g.id = :userId ORDER BY m.starttime ASC")
    List<MeetingEntity> findAllMeetingsByGuestUserId(@Param("userId") Long userId);
}


