package com.dk98126.mireabankapp.controller;

import com.dk98126.mireabankapp.exception.LoginExistsException;
import com.dk98126.mireabankapp.exception.PhoneNumberExistsException;
import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import com.dk98126.mireabankapp.model.form.CreateAccountRequestForm;
import com.dk98126.mireabankapp.model.form.RegisterUserForm;
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
            login = authentication.getName();
        }
        model.addAttribute("name", login);
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
    public String loginPage() {
        return "login";
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
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        accountRequestService.createRequest(form, login);
        return "success-create-request";
    }

    @GetMapping("/request-statuses")
    public String showStatusesPage(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountRequestStatusEntity> statuses = accountRequestService.findAllStatuses(login);
        //TODO настроить стили на странице request-statuses
        model.addAttribute("statuses", statuses);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        model.addAttribute("dateFormatter", dateFormatter);
        return "request-statuses";
    }
}
