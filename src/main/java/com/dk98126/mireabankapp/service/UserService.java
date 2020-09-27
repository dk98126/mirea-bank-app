package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.model.dto.UserDto;
import com.dk98126.mireabankapp.model.enm.UserRole;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.RegisterUserForm;
import com.dk98126.mireabankapp.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void registerUser(RegisterUserForm form) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(form.getLogin());
        userEntity.setFirstName(form.getFirstName());
        userEntity.setMiddleName(form.getMiddleName());
        userEntity.setLastName(form.getLastName());
        // TODO парсить номер телефона
        userEntity.setPhoneNumber(formatPhoneNumber(form.getPhoneNumber()));
        userEntity.setPassword(form.getPassword());
        userEntity.setRole(UserRole.USER);
        userRepo.save(userEntity);
    }

    private String formatPhoneNumber(String phoneNumber) {
        String numberFromDigits = phoneNumber.replaceAll("[-+\\- ()]", "");
        return "7" + numberFromDigits.substring(numberFromDigits.length() - 10);
    }

    public List<UserDto> getUsers() {
        return userRepo.findAll().stream().map(entity -> {
            UserDto userDto = new UserDto();
            userDto.setName(entity.getFirstName());
            return userDto;
        }).collect(Collectors.toList());
    }
}
