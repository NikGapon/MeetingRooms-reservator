package com.nikgapon.MeetingRoomsReservator.repository;

import com.nikgapon.MeetingRoomsReservator.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByLoginIgnoreCase(String login);
}
