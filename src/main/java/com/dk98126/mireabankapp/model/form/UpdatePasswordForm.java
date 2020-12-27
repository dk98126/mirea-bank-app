package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.PasswordConstraint;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordForm {
    @NotBlank
    @PasswordConstraint
    String newPassword;
    @NotBlank
    String oldPassword;
}
