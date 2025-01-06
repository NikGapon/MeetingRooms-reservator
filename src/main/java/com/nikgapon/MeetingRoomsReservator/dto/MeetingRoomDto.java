package com.nikgapon.MeetingRoomsReservator.dto;

import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class MeetingRoomDto {
    private Long id;
    private String name;
    private String description;


    public MeetingRoomDto(MeetingRoomEntity meetingRoomEntity) {
        this.id = meetingRoomEntity.getId();
        this.name = meetingRoomEntity.getName();
        this.description = meetingRoomEntity.getDescription();
    }

}
