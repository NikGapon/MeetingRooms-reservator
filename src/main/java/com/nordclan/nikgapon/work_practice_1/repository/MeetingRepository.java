package com.nordclan.nikgapon.work_practice_1.repository;

import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    @Query(value = "SELECT * FROM public.meeting WHERE starttime > ?1 AND endtime < ?2",nativeQuery = true)
    List<MeetingEntity> findByTimeInterval(LocalDateTime startDate, LocalDateTime endDate);

    //@Query(value = "SELECT m FROM meeting m WHERE m.meetingroom_id = :meetingroom_id AND (m.starttime < :endtime AND m.endtime > :starttime)")
    //List<MeetingEntity> findByRoomAndTimeRange(@Param("meetingroom_id") MeetingRoomEntity meetingroom_id,
     //                                    @Param("starttime") Timestamp startTime,
       //                                  @Param("endtime") Timestamp endTime);

    @Query("SELECT m FROM MeetingEntity m WHERE m.room = :meetingRoom AND (m.starttime < :endtime AND m.endtime > :starttime)")
    List<MeetingEntity> findByRoomAndTimeRange(@Param("meetingRoom") MeetingRoomEntity meetingRoom,
                                               @Param("starttime") Timestamp starttime,
                                               @Param("endtime") Timestamp endtime);

}


