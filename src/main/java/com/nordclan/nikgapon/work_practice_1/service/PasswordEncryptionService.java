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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void encryptAllPasswords() {
        // Извлекаем всех пользователей
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity user : users) {
            // Проверяем, зашифрован ли пароль
            if (!isPasswordEncrypted(user.getPassword())) {
                // Если не зашифрован, то шифруем
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);
                userRepository.save(user);  // Обновляем пользователя в базе данных
            }
        }
    }

    // Метод для проверки, зашифрован ли пароль
    private boolean isPasswordEncrypted(String password) {
        // Пароли, зашифрованные BCrypt, начинаются с "$2a$" или "$2b$"
        return password.startsWith("$2a$") || password.startsWith("$2b$");
    }
}

