package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.PhoneNumberConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdatePhoneNumberForm {
    @NotBlank
    @PhoneNumberConstraint
    private String phoneNumber;
}
