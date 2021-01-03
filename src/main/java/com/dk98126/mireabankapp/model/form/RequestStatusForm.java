package com.dk98126.mireabankapp.model.form;

import com.dk98126.mireabankapp.model.enm.AccountRequestTransitionStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestStatusForm {
    @NotBlank
    AccountRequestTransitionStatus accountRequestTransitionStatus;
}
