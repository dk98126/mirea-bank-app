package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.exception.LoginExistsException;
import com.dk98126.mireabankapp.exception.PhoneNumberExistsException;
import com.dk98126.mireabankapp.model.dto.UserDto;
import com.dk98126.mireabankapp.model.enm.UserRole;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.RegisterUserForm;
import com.dk98126.mireabankapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // TODO вместо исключений  возвращать список из fieldError
    public void registerUser(RegisterUserForm form) {
        String login = form.getLogin();
        String phoneNumber = formatPhoneNumber(form.getPhoneNumber());
        if (userRepo.existsByLogin(login)) {
            throw new LoginExistsException();
        } else if (userRepo.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberExistsException();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setFirstName(form.getFirstName());
        userEntity.setMiddleName(form.getMiddleName());
        userEntity.setLastName(form.getLastName());
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
        userEntity.setRole(UserRole.USER);
        userRepo.save(userEntity);
    }

    private String formatPhoneNumber(String phoneNumber) {
        String numberFromDigits = phoneNumber.replaceAll("[-+ ()]", "");
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
