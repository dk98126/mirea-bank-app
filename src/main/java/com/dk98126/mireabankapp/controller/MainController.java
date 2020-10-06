package com.dk98126.mireabankapp.controller;

import com.dk98126.mireabankapp.exception.LoginExistsException;
import com.dk98126.mireabankapp.exception.PhoneNumberExistsException;
import com.dk98126.mireabankapp.model.form.RegisterUserForm;
import com.dk98126.mireabankapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MainController {

    private final UserService userService;

    private final AtomicInteger counter = new AtomicInteger();

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "name", defaultValue = "Незнакомец") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("counter", counter.incrementAndGet());
        return "main";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("form") RegisterUserForm form) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("form") @Valid RegisterUserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.registerUser(form);
        } catch (LoginExistsException e) {
            FieldError fieldError = new FieldError("form", "login", "Логин занят");
            bindingResult.addError(fieldError);
            return "registration";
        } catch (PhoneNumberExistsException e) {
            FieldError fieldError = new FieldError("form", "phoneNumber", "Телефон уже зарегистрирован");
            bindingResult.addError(fieldError);
            return "registration";
        }
        return "redirect:/my-room";
    }
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("form") RegisterUserForm form) {
        return "login";
    }
    @GetMapping("/my-room")
    public String myRoomPage(@ModelAttribute("form") RegisterUserForm form) {
        return "my-room";
    }
}
