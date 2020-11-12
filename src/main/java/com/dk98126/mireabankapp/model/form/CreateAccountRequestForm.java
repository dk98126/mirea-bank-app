package com.dk98126.mireabankapp.model.form;

import com.dk98126.mireabankapp.model.enm.AccountType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAccountRequestForm {
    @NotBlank
    AccountType accountType;
}
