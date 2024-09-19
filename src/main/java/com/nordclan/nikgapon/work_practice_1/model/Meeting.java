package com.nordclan.nikgapon.work_practice_1.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity creator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meetingroom_id")
    private MeetingRoom room;

    @ManyToMany
    @JoinColumn(name= "user_id", nullable = false)
    private List<UserEntity> guests;

    private Timestamp starttime;
    private Timestamp endtime;

    public Long getId() {
        return id;
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


    public Meeting(){

    }

    public boolean cheekers(Timestamp new_time){
        if (starttime.compareTo(new_time) > 0 && endtime.compareTo(new_time) < 0){
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", room='" + room + '\'' +
                ", guests='" + guests + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }

}
