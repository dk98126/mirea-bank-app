package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.FirstNameConstraint;
import validators.annotation.LastNameConstraint;
import validators.annotation.MiddleNameConstraint;
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
    @FirstNameConstraint
    private String firstName;

    @MiddleNameConstraint
    private String middleName;

    @NotBlank
    @LastNameConstraint
    private String lastName;

    @NotBlank
    @PasswordConstraint
    private String phoneNumber;
}
