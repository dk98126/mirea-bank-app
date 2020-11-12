package com.dk98126.mireabankapp.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdatePhoneNumberForm {
    @NotBlank
    @Pattern(regexp = "^(\\+7|7|8)?[( \\-]?\\d{3}[) \\-]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$", message = "Должен быть введен номер телефона")
    private String phoneNumber;
}
