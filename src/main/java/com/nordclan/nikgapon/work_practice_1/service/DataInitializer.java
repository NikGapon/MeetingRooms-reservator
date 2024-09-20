package com.nordclan.nikgapon.work_practice_1.service;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    @PostConstruct
    public void init() {
        passwordEncryptionService.encryptAllPasswords();
    }
}