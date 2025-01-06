package com.nikgapon.MeetingRoomsReservator.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + this.name();
    }

}