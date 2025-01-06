package com.nikgapon.MeetingRoomsReservator.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToTimestampConverter implements Converter<String, Timestamp> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public Timestamp convert(String source) {
        if (source.isEmpty()) return null;

        LocalDateTime localDateTime = LocalDateTime.parse(source, FORMATTER);
        return Timestamp.valueOf(localDateTime);
    }
}
