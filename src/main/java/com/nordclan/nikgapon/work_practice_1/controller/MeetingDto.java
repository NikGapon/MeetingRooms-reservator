package com.nordclan.nikgapon.work_practice_1.controller;

import com.nordclan.nikgapon.work_practice_1.model.MeetingEntity;
import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import com.nordclan.nikgapon.work_practice_1.model.UserEntity;

import java.sql.Timestamp;
import java.util.List;

public class MeetingDto {

    private Long id;
    private String title;
    private String description;
    private UserEntity creator;
    private MeetingRoomEntity room;
    private List<UserEntity> guests;
    private Timestamp starttime;
    private Timestamp endtime;

    public MeetingDto() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public MeetingRoomEntity getRoom() {
        return room;
    }

    public void setRoom(MeetingRoomEntity room) {
        this.room = room;
    }
    public void setMeeting_id(MeetingRoomEntity room){
        this.room = room;
    }

    public List<UserEntity> getGuests() {
        return guests;
    }

    public void setGuests(List<UserEntity> guests) {
        this.guests = guests;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
