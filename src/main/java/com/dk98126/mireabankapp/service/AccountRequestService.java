package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.model.entity.AccountRequestEntity;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.CreateAccountRequestForm;
import com.dk98126.mireabankapp.repository.AccountRequestRepo;
import com.dk98126.mireabankapp.repository.UserRepo;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountRequestService {
    private final AccountRequestRepo accountRequestRepo;
    private final UserRepo userRepo;

    public AccountRequestService(AccountRequestRepo accountRequestRepo, UserRepo userRepo) {
        this.accountRequestRepo = accountRequestRepo;
        this.userRepo = userRepo;
    }

    public void createRequest(@NotNull CreateAccountRequestForm form, String userLogin) {
        UserEntity userEntity = userRepo.findByLogin(userLogin);
        AccountRequestEntity accountRequestEntity = new AccountRequestEntity();
        accountRequestEntity.setType(form.getAccountType());
        accountRequestEntity.setUser(userEntity);
        accountRequestRepo.save(accountRequestEntity);
    }
}
