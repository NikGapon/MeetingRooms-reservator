package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.MeetingRoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MeetingRoomEntityConverter implements Converter<String, MeetingRoomEntity> {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @Override
    public MeetingRoomEntity convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        System.out.println("1111");
        System.out.println(source);
        System.out.println(meetingRoomService.findRoom(Long.parseLong(source)));
        return meetingRoomService.findRoom(Long.parseLong(source));
    }
}