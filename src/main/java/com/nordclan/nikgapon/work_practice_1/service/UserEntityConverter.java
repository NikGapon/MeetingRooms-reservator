package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConverter implements Converter<String, UserEntity> {

    private final UserService userService;

    @Autowired
    public UserEntityConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity convert(String source) {
        if (source.isEmpty()) return null;

        return userService.findUser(Long.parseLong(source));
    }
}