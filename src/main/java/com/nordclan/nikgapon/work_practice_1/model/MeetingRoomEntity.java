package com.nordclan.nikgapon.work_practice_1.model;


import com.nordclan.nikgapon.work_practice_1.controller.MeetingRoomDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meetingroom")
public class MeetingRoomEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

    @Size(min = 0, max = 516)
    private String description;

    @OneToMany(mappedBy = "room", cascade={CascadeType.ALL},  orphanRemoval = true)
    private List<MeetingEntity> meeting;

    public MeetingRoomEntity(){}

    public MeetingRoomEntity(String name){
        this.name = name;
    }

    public MeetingRoomEntity(String name, String description){
        this(name);
        this.description = description;
    }

    public MeetingRoomEntity(MeetingRoomDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MeetingEntity> getMeeting() { return meeting; }

    public void setMeeting(List<MeetingEntity> meeting) { this.meeting = meeting; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoomEntity room = (MeetingRoomEntity) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public String toString() {
        return "MeetingRoomEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
