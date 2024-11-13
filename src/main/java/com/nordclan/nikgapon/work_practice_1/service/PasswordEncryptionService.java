package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.UserEntity;
import com.nordclan.nikgapon.work_practice_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PasswordEncryptionService {
    // A small class reencrypts passwords at the start of the password encryption program,
    // if we enter them directly into BD
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void encryptAllPasswords() {
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity user : users) {
            if (!isPasswordEncrypted(user.getPassword())) {
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);
                userRepository.save(user);
            }
        }
    }

    private boolean isPasswordEncrypted(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$");
    }
}

