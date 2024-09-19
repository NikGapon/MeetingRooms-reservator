package com.nordclan.nikgapon.work_practice_1.service;

import com.nordclan.nikgapon.work_practice_1.model.UserEntity;
import com.nordclan.nikgapon.work_practice_1.model.UserRole;
import com.nordclan.nikgapon.work_practice_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }
    /*@Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // не будем ахахахахахахахаха
        if ("user".equals(login)) {
            return UserEntity.withUsername("user")
                    .password("password")
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("UserEntity not found");
        }

    }*/

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        final UserEntity userEntity = findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}