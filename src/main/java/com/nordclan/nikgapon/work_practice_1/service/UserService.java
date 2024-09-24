package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.UserEntity;
import com.nordclan.nikgapon.work_practice_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        final UserEntity userEntity = findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }

    public boolean AdminCheek(Long id){
        // todo Написать проверку на Роль
        return true;
    }
    public boolean AdminCheek(String login){
        // todo И сюда тоже админ чек
        return true;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(String login, String rawPassword, String role) {
        UserEntity user = new UserEntity();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        userRepository.save(user);
    }

}