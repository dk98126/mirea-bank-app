package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @PhoneNumberConstraint
    private String phoneNumber;
}
