package com.dk98126.mireabankapp.repository;

import com.dk98126.mireabankapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
