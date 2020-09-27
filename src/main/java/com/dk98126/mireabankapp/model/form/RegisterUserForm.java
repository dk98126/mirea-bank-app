package com.dk98126.mireabankapp.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterUserForm {
    @NotBlank
    String login;

    // TODO исправить валидацию пароля
    @NotNull
    @Pattern(regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#'.,:^)(*$%=+!;\"-]).{8,64})", message = "Пароль должен соответствовать определенному паттерну")
    String password;

    // TODO валидация ФИО
    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(\\+7|7|8)?[( \\-]?\\d{3}[) \\-]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$", message = "Должен быть введен номер телефона")
    private String phoneNumber;
}
