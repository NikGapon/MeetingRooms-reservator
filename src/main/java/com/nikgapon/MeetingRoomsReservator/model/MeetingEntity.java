package com.nikgapon.MeetingRoomsReservator.model;

import com.nikgapon.MeetingRoomsReservator.dto.MeetingDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "meeting")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MeetingEntity {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column(nullable = false, length = 64)
    @Getter
    @Setter
    private String title;

    @Size(min = 0, max = 516)
    @Getter
    @Setter
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    @Getter
    @Setter
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "meetingroom_id")
    @Getter
    @Setter
    private MeetingRoomEntity room;

    @ManyToMany
    @JoinTable(name= "guest", joinColumns = { @JoinColumn(name = "meeting_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")})
    @Getter
    @Setter
    private List<UserEntity> guests;

    @Getter
    @Setter
    private Timestamp starttime;

    @Getter
    @Setter
    private Timestamp endtime;

    public MeetingEntity(MeetingDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.creator = dto.getCreator();
        this.room = dto.getRoom();
        this.guests = dto.getGuests();
        this.starttime = dto.getStarttime();
        this.endtime = dto.getEndtime();
    }

    public MeetingEntity(String title, String description, UserEntity creator, MeetingRoomEntity room,
                         List<UserEntity> guests, Timestamp starttime, Timestamp endtime) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.room = room;
        this.guests = guests;
        this.starttime = starttime;
        this.endtime = endtime;
    }


}
