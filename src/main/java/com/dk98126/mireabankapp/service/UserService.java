package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.dto.UserDto;
import com.dk98126.mireabankapp.entity.UserEntity;
import com.dk98126.mireabankapp.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void registerUser(String name, Integer age) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(age);
        userEntity.setName(name);
        userRepo.save(userEntity);
    }

    public List<UserDto> getUsers() {
        return userRepo.findAll().stream().map(entity -> {
            UserDto userDto = new UserDto();
            userDto.setAge(entity.getAge());
            userDto.setName(entity.getName());
            return userDto;
        }).collect(Collectors.toList());
    }
}
