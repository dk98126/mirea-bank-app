package com.dk98126.mireabankapp.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterUserForm {
    @NotBlank
    String login;

    @NotNull
    @Pattern(regexp = "^(?=[a-zA-Z\\d@#'.,:^)(*$%=+!;\"-]{0,63}[a-z])(?=[a-zA-Z\\d@#'.,:^)(*$%=+!;\"-]{0,63}[A-Z])(?=[a-zA-Z\\d@#'.,:^)(*$%=+!;\"-]{0,63}\\d)(?=[a-zA-Z\\d@#'.,:^)(*$%=+!;\"-]{0,63}[@#'.,:^)(*$%=+!;\"-])[a-zA-Z\\d@#'.,:^)(*$%=+!;\"-]{8,64}$", message = "Пароль должен соответствовать определенному паттерну")
    String password;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*", message = "Имя должно содержать только символы кириллицы и начинаться с прописной буквы")
    private String firstName;

    @Pattern(regexp = "^([А-Я][а-я]*)?$", message = "Отчество(при наличии) должно содержать только символы кириллицы и начинаться с прописной буквы")
    private String middleName;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*", message = "Фамилия должна содержать только символы кириллицы и начинаться с прописной буквы")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(\\+7|7|8)?[( \\-]?\\d{3}[) \\-]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$", message = "Должен быть введен номер телефона")
    private String phoneNumber;
}
