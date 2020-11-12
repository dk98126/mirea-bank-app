package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.exception.*;
import com.dk98126.mireabankapp.model.dto.UserDto;
import com.dk98126.mireabankapp.model.enm.UserRole;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.RegisterUserForm;
import com.dk98126.mireabankapp.model.form.UpdateLoginForm;
import com.dk98126.mireabankapp.model.form.UpdateMailForm;
import com.dk98126.mireabankapp.model.form.UpdatePasswordForm;
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
        String mail = form.getMail();
        if (userRepo.existsByLogin(login)) {
            throw new LoginExistsException();
        } else if (userRepo.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberExistsException();
        } else if (userRepo.existsByMail(mail)) {
            throw new MailExistsException();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setFirstName(form.getFirstName());
        userEntity.setMiddleName(form.getMiddleName());
        userEntity.setLastName(form.getLastName());
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setMail(mail);
        userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
        userEntity.setRole(UserRole.USER);
        userRepo.save(userEntity);
    }

    private String formatPhoneNumber(String phoneNumber) {
        String numberFromDigits = phoneNumber.replaceAll("[-+ ()]", "");
        return "7" + numberFromDigits.substring(numberFromDigits.length() - 10);
    }

    public UserEntity findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void updatePassword(UserEntity user, UpdatePasswordForm form) {
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword()))
            throw new WrongPasswordException();
        if (passwordEncoder.matches(form.getNewPassword(), user.getPassword()))
            throw new NewPasswordEqualsOldPasswordException();
        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userRepo.save(user);
    }

    public void updateLogin(UserEntity user, UpdateLoginForm form) {
        String login = form.getNewLogin();
        if (userRepo.existsByLogin(login)) {
            throw new LoginExistsException();
        }
        user.setLogin(login);
        userRepo.save(user);
    }

    public void updateMail(UserEntity user, UpdateMailForm form) {
        String mail = form.getNewMail();
        if (userRepo.existsByMail(mail)) {
            throw new MailExistsException();
        }
        user.setMail(mail);
        userRepo.save(user);
    }

    public List<UserDto> getUsers() {
        return userRepo.findAll().stream().map(entity -> {
            UserDto userDto = new UserDto();
            userDto.setName(entity.getFirstName());
            return userDto;
        }).collect(Collectors.toList());
    }
}
