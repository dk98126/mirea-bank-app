package com.dk98126.mireabankapp.model.enm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    DEBIT("Дебитовый"),
    CREDIT("Кредитный");
    private String accountTypeName;
}
