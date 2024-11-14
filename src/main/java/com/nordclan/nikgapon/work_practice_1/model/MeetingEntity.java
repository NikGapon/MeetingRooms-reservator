package com.nordclan.nikgapon.work_practice_1.model;

import com.nordclan.nikgapon.work_practice_1.dto.MeetingDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "meeting")
public class MeetingEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 64)
    private String title;

    @Size(min = 0, max = 516)
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "meetingroom_id")
    private MeetingRoomEntity room;

    @ManyToMany
    @JoinTable(name= "guest", joinColumns = { @JoinColumn(name = "meeting_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")})
    private List<UserEntity> guests;

    private Timestamp starttime;
    private Timestamp endtime;

    public Long getId() {
        return id;
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

    public Timestamp getStarttime(){
        return starttime;
    }

    public void setStarttime(Timestamp starttime){
        this.starttime = starttime;
    }

    public Timestamp getEndtime(){
        return endtime;
    }

    public void setEndtime(Timestamp endtime){
        this.endtime = endtime;
    }

    public MeetingRoomEntity getRoom() {
        return room;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public void setRoom(MeetingRoomEntity room) {
        this.room = room;
    }

    public List<UserEntity> getGuests() {
        return guests;
    }

    public void setGuests(List<UserEntity> guests) {
        this.guests = guests;
    }

    public MeetingEntity(){}

    public MeetingEntity(MeetingDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.creator = dto.getCreator();
        this.room = dto.getRoom();
        this.guests = dto.getGuests();
        this.starttime = dto.getStarttime();
        this.endtime = dto.getEndtime();
    }

    public MeetingEntity( String title, String description, UserEntity creator, MeetingRoomEntity room, List<UserEntity> guests, Timestamp starttime, Timestamp endtime) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.room = room;
        this.guests = guests;
        this.starttime = starttime;
        this.endtime = endtime;
    }


    @Override
    public String toString() {
        return "MeetingEntity{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", room='" + room + '\'' +
                ", guests='" + guests + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }
}
