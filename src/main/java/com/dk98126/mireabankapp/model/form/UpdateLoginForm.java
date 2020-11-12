package com.dk98126.mireabankapp.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateLoginForm {
    @NotBlank
    String newLogin;
}
