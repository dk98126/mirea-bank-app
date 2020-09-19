package com.dk98126.mireabankapp.controller;

import com.dk98126.mireabankapp.dto.UserDto;
import com.dk98126.mireabankapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto.getName(), userDto.getAge());
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }
}
