package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.PasswordConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterUserForm {
    @NotBlank
    String login;

    @NotBlank
    @PasswordConstraint
    String password;

    @NotBlank
    @Email
    String mail;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "Имя должно содержать только символы кириллицы и начинаться с прописной буквы")
    private String firstName;

    @Pattern(regexp = "^([А-Я][а-я]*)?$", message = "Отчество должно содержать только символы кириллицы и начинаться с прописной буквы")
    private String middleName;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "Фамилия должна содержать только символы кириллицы и начинаться с прописной буквы")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(\\+7|7|8)?[( \\-]?\\d{3}[) \\-]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$", message = "Должен быть введен номер телефона")
    private String phoneNumber;
}
