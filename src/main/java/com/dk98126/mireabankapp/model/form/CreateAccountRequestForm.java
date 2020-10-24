package com.dk98126.mireabankapp.model.form;

import com.dk98126.mireabankapp.model.enm.AccountType;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import lombok.Data;

@Data
public class CreateAccountRequestForm {
    AccountType accountType;
}
