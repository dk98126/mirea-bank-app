package com.dk98126.mireabankapp.controller;

import com.dk98126.mireabankapp.exception.*;
import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.*;
import com.dk98126.mireabankapp.service.AccountRequestService;
import com.dk98126.mireabankapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;
    private final AccountRequestService accountRequestService;
    private final AtomicInteger counter = new AtomicInteger();

    //TODO разобраться почему при каждом открытии единственной вкладки счетчик увеличивается на 2
    @GetMapping("/main")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = "Незакомец";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserEntity userEntity = userService.findUserById(authentication.getName());
            login = userEntity.getLogin();
        }
        model.addAttribute("name", login);
        model.addAttribute("counter", counter.incrementAndGet());
        return "main";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("form") RegisterUserForm form) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/my-room";
        } else return "registration";
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
        } catch (MailExistsException e) {
            FieldError fieldError = new FieldError("form", "mail", "Пользователь с такой почтой уже зарегистрирован");
            bindingResult.addError(fieldError);
            return "registration";
        }
        return "redirect:/my-room";
    }

    @GetMapping("/login")
    public String loginPage() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/my-room";
        } else return "login";
    }

    @GetMapping("/my-room")
    public String myRoomPage() {
        return "my-room";
    }

    @GetMapping("/create-request")
    public String createRequestPage() {
        return "create-request";
    }

    @PostMapping("/create-request")
    public String createRequest(@ModelAttribute("form") CreateAccountRequestForm form) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        accountRequestService.createRequest(form, id);
        return "success-create-request";
    }

    @GetMapping("/request-statuses")
    public String showStatusesPage(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountRequestStatusEntity> statuses = accountRequestService.findAllStatuses(id);
        //TODO настроить стили на странице request-statuses
        model.addAttribute("statuses", statuses);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        model.addAttribute("dateFormatter", dateFormatter);
        return "request-statuses";
    }

    @GetMapping("/account-settings")
    public String accountSettingsPage() {
        return "account-settings";
    }

    @GetMapping("/update-login")
    public String updateLoginPage(@ModelAttribute("form") UpdateLoginForm form) {
        return "update-login";
    }

    @PostMapping("/update-login")
    public String updateLogin(@ModelAttribute("form") @Valid UpdateLoginForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-login";
        }
        UserEntity user = userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            userService.updateLogin(user, form);
        } catch (LoginExistsException e) {
            FieldError fieldError = new FieldError("form", "newLogin", "Пользователь с таким логином уже существует");
            bindingResult.addError(fieldError);
            return "update-login";
        }

        return "success-update-login";
    }

    @GetMapping("/update-mail")
    public String updateMailPage(@ModelAttribute("form") UpdateMailForm form) {
        return "update-mail";
    }

    @PostMapping("/update-mail")
    public String updateMail(@ModelAttribute("form") UpdateMailForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-mail";
        }
        UserEntity user = userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            userService.updateMail(user, form);
        } catch (MailExistsException e) {
            FieldError fieldError = new FieldError("form", "newMail", "Пользователь с такой почтой уже существует");
            bindingResult.addError(fieldError);
            return "update-mail";
        }
        return "success-update-mail";
    }

    @GetMapping("/update-password")
    public String updatePasswordPage(@ModelAttribute("form") UpdatePasswordForm form) {
        return "update-password";
    }

    @PostMapping("/update-password")
    public String updatePassword(@ModelAttribute("form") @Valid UpdatePasswordForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-password";
        }
        UserEntity user = userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            userService.updatePassword(user, form);
        } catch (WrongPasswordException e) {
            FieldError fieldError = new FieldError("form", "oldPassword", "Неверный старый пароль");
            bindingResult.addError(fieldError);
            return "update-password";
        } catch (NewPasswordEqualsOldPasswordException e) {
            FieldError fieldError = new FieldError("form", "newPassword", "Новый пароль совпадает со старым");
            bindingResult.addError(fieldError);
            return "update-password";
        }
        return "success-update-password";
    }

    @GetMapping("/update-full-name")
    public String updateFullNamePage(@ModelAttribute("form") UpdateFullNameForm form) {
        return "update-full-name";
    }

    @PostMapping("/update-full-name")
    public String updateFullName(@ModelAttribute("form") @Valid UpdateFullNameForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-full-name";
        }
        UserEntity user = userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.updateFullName(user, form);
        return "success-update-full-name";
    }

    @GetMapping("/update-phone-number")
    public String updatePhoneNumberPage(@ModelAttribute("form") UpdatePhoneNumberForm form) {
        return "update-phone-number";
    }

    @PostMapping("/update-phone-number")
    public String updatePhoneNumber(@ModelAttribute("form") @Valid UpdatePhoneNumberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-phone-number";
        }
        UserEntity user = userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            userService.updatePhoneNumber(user, form);
        } catch (PhoneNumberExistsException e) {
            FieldError fieldError = new FieldError("form", "phoneNumber", "Пользователь с таким номером телефона уже существует");
            bindingResult.addError(fieldError);
            return "update-phone-number";
        }
        return "success-update-phone-number";
    }
}
