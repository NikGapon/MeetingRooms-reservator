package com.nordclan.nikgapon.work_practice_1.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "meetingroom")
public class  MeetingRoom {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

    @Size(min = 0, max = 516)
    private String description;

    public MeetingRoom(){
    }

    public MeetingRoom(String name){
        this.name = name;
    }

    public MeetingRoom(String name, String description){
        this(name);
        this.description = description;
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

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +

                '}';
    }

}
