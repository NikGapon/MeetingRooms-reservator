package com.nikgapon.MeetingRoomsReservator.service;

import com.nikgapon.MeetingRoomsReservator.model.MeetingRoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MeetingRoomEntityConverter implements Converter<String, MeetingRoomEntity> {

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public MeetingRoomEntityConverter(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @Override
    public MeetingRoomEntity convert(String source) {
        if (source.isEmpty()) return null;

        return meetingRoomService.findRoom(Long.parseLong(source));
    }
}