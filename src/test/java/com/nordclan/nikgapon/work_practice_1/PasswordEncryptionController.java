package com.nordclan.nikgapon.work_practice_1;

import com.nordclan.nikgapon.work_practice_1.service.PasswordEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordEncryptionController {
    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    @PostMapping("/public/encrypt-passwords")
    public void encryptPasswords() {
        passwordEncryptionService.encryptAllPasswords();
        //return "Пароли успешно зашифрованы!";
    }
}
