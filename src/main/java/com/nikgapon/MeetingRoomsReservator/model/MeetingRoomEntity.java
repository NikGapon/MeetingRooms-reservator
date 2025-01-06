package com.nikgapon.MeetingRoomsReservator.model;


import com.nikgapon.MeetingRoomsReservator.dto.MeetingRoomDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meetingroom")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MeetingRoomEntity {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    @Getter
    @Setter
    private String name;

    @Size(min = 0, max = 516)
    @Getter
    @Setter
    private String description;

    @OneToMany(mappedBy = "room", cascade={CascadeType.ALL},  orphanRemoval = true)
    @Getter
    @Setter
    private List<MeetingEntity> meeting;


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

}
