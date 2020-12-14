package com.dk98126.mireabankapp.model.enm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRequestTransitionStatus {
    CREATED("Создана"),
    IN_PROGRESS("В обслуживании"),
    APPROVED("Одобрена"),
    DECLINED("Отклонена");
    private String RequestTransitionStatusName;
}
