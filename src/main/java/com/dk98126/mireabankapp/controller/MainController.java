package com.dk98126.mireabankapp.controller;

import com.dk98126.mireabankapp.exception.*;
import com.dk98126.mireabankapp.model.BankUserPrincipal;
import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.*;
import com.dk98126.mireabankapp.service.AccountRequestService;
import com.dk98126.mireabankapp.service.AccountService;
import com.dk98126.mireabankapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dk98126.mireabankapp.utils.ControllerUtils.*;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;
    private final AccountRequestService accountRequestService;
    private final AtomicInteger counter = new AtomicInteger();
    private final AccountService accountService;

    //TODO разобраться почему при каждом открытии единственной вкладки счетчик увеличивается на 2
    @GetMapping("/main")
    public String mainPage(Model model) {
        Authentication authentication = getAuthenticationFromSecurityContextHolder();
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
        return redirectIfAuthenticated("/my-room", "registration");
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("form") @Valid RegisterUserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.registerUser(form);
        } catch (LoginExistsException e) {
            addFieldError(bindingResult, "form", "login", "Логин занят");
            return "registration";
        } catch (PhoneNumberExistsException e) {
            addFieldError(bindingResult, "form", "phoneNumber", "Телефон уже зарегистрирован");
            return "registration";
        } catch (MailExistsException e) {
            addFieldError(bindingResult, "form", "mail", "Пользователь с такой почтой уже зарегистрирован");
            return "registration";
        }
        return "redirect:/my-room";
    }

    @GetMapping("/login")
    public String loginPage() {
        return redirectIfAuthenticated("/my-room", "login");
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
        String login = getNameFromSecurityContextHolder();
        accountRequestService.createRequest(form, login);
        return "success-create-request";
    }

    @GetMapping("/request-statuses")
    public String showStatusesPage(Model model) {
        String login = getNameFromSecurityContextHolder();
        List<AccountRequestStatusEntity> statuses = accountRequestService.findAllStatuses(login);
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
        Authentication authentication = getAuthenticationFromSecurityContextHolder();
        UserEntity user = userService.findUserByLogin(authentication.getName());
        try {
            userService.updateLogin(user, form);
            BankUserPrincipal bankUserPrincipal = (BankUserPrincipal) authentication.getPrincipal();
            bankUserPrincipal.setLogin(user.getLogin());
        } catch (LoginExistsException e) {
            addFieldError(bindingResult, "form", "newLogin", "Пользователь с таким логином уже существует");
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
        Authentication authentication = getAuthenticationFromSecurityContextHolder();
        UserEntity user = userService.findUserByLogin(authentication.getName());
        try {
            userService.updateMail(user, form);
            BankUserPrincipal bankUserPrincipal = (BankUserPrincipal) authentication.getPrincipal();
            bankUserPrincipal.setMail(user.getMail());

        } catch (MailExistsException e) {
            addFieldError(bindingResult, "form", "newMail", "Пользователь с такой почтой уже существует");
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
        UserEntity user = userService.findUserByLogin(getNameFromSecurityContextHolder());
        try {
            userService.updatePassword(user, form);
        } catch (WrongPasswordException e) {
            addFieldError(bindingResult, "form", "oldPassword", "Неверный старый пароль");
            return "update-password";
        } catch (NewPasswordEqualsOldPasswordException e) {
            addFieldError(bindingResult, "form", "newPassword", "Новый пароль совпадает со старым");
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
        Authentication authentication = getAuthenticationFromSecurityContextHolder();
        UserEntity user = userService.findUserByLogin(authentication.getName());
        userService.updateFullName(user, form);
        BankUserPrincipal bankUserPrincipal = (BankUserPrincipal) authentication.getPrincipal();
        bankUserPrincipal.setFullName(user.getFirstName(), user.getMiddleName(), user.getLastName());
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
        Authentication authentication = getAuthenticationFromSecurityContextHolder();
        UserEntity user = userService.findUserByLogin(authentication.getName());
        try {
            userService.updatePhoneNumber(user, form);
            BankUserPrincipal bankUserPrincipal = (BankUserPrincipal) authentication.getPrincipal();
            bankUserPrincipal.setPhoneNumber(user.getPhoneNumber());
        } catch (PhoneNumberExistsException e) {
            addFieldError(bindingResult, "form", "phoneNumber", "Пользователь с таким номером телефона уже существует");
            return "update-phone-number";
        }
        return "success-update-phone-number";
    }

    @GetMapping("/users-statuses")
    public String showUsersStatusesPage(Model model, RequestStatusForm form) {
        List<AccountRequestStatusEntity> statuses = accountService.findALlRequests(form.getAccountRequestTransitionStatus().name());
        model.addAttribute("statuses", statuses);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        model.addAttribute("dateFormatter", dateFormatter);
     /*   for(int i=0;i<statuses.size();i++)
        {
            model.addAttribute("status"+i, statuses.get(i));
        }*/

        return "users-statuses";
    }

    @GetMapping("/managing-requests")
    public String managingRequestsPage() {
        return "managing-requests";
    }

    @PostMapping("/managing-requests")
    public String managingRequests(Model model, @ModelAttribute("form") RequestStatusForm form) {
        return showUsersStatusesPage(model, form);
    }
}
