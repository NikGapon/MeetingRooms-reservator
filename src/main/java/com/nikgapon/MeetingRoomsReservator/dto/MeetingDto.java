package com.nikgapon.MeetingRoomsReservator.dto;

import com.nikgapon.MeetingRoomsReservator.model.MeetingEntity;
import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import com.nikgapon.MeetingRoomsReservator.model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MeetingDto {

    private Long id;
    private String title;
    private String description;
    private UserEntity creator;
    private MeetingRoomEntity room;
    private List<UserEntity> guests;
    private Timestamp starttime;
    private Timestamp endtime;


    public MeetingDto(MeetingEntity meetingEntity) {
        this.id = meetingEntity.getId();
        this.title = meetingEntity.getTitle();
        this.description = meetingEntity.getDescription();
        this.starttime = meetingEntity.getStarttime();
        this.endtime = meetingEntity.getEndtime();
        this.creator = meetingEntity.getCreator();
        this.room = meetingEntity.getRoom();
        this.guests = meetingEntity.getGuests();
    }

}
