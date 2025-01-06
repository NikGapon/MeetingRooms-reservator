package com.nikgapon.MeetingRoomsReservator.service;

import com.nikgapon.MeetingRoomsReservator.model.UserEntity;
import com.nikgapon.MeetingRoomsReservator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        if (userEntity == null) throw new UsernameNotFoundException(login);

        return new User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
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

    public UserEntity findUser(long id) {
        final Optional<UserEntity> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }
}


class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super(String.format("USer with id [%s] ius not found", id ));
    }
}
