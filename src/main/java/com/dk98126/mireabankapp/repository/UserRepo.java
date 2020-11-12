package com.dk98126.mireabankapp.repository;

import com.dk98126.mireabankapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    boolean existsByLogin(String login);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByMail(String mail);

    UserEntity findByLogin(String login);
    /*UserEntity findById(String id);*/
}
