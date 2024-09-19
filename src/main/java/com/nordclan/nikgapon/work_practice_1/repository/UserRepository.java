package com.nordclan.nikgapon.work_practice_1.repository;

import com.nordclan.nikgapon.work_practice_1.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByLoginIgnoreCase(String login);
    //Optional<UserEntity> findByLogin(String login);

}
