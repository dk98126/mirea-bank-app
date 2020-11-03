package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.model.enm.AccountRequestTransitionStatus;
import com.dk98126.mireabankapp.model.entity.AccountRequestEntity;
import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.model.form.CreateAccountRequestForm;
import com.dk98126.mireabankapp.repository.AccountRequestRepo;
import com.dk98126.mireabankapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountRequestService {
    private final AccountRequestRepo accountRequestRepo;
    private final UserRepo userRepo;

    public void createRequest(@NotNull CreateAccountRequestForm form, String userLogin) {
        UserEntity userEntity = userRepo.findByLogin(userLogin);
        AccountRequestEntity accountRequestEntity = new AccountRequestEntity();
        accountRequestEntity.setType(form.getAccountType());
        accountRequestEntity.setUser(userEntity);
        AccountRequestStatusEntity accountRequestStatusEntity = new AccountRequestStatusEntity();
        accountRequestStatusEntity.setTransitionTime(LocalDateTime.now());
        accountRequestStatusEntity.setStatus(AccountRequestTransitionStatus.IN_PROGRESS);
        accountRequestStatusEntity.setRequest(accountRequestEntity);
        accountRequestStatusEntity.setInitiator(accountRequestEntity.getUser());
        accountRequestEntity.getStatuses().add(accountRequestStatusEntity);
        accountRequestRepo.save(accountRequestEntity);
        System.out.println();

    }

    public List<AccountRequestStatusEntity> findAll(String userLogin) {
        List<AccountRequestEntity> requests = userRepo.findByLogin(userLogin).getAccountRequests();
        List<AccountRequestStatusEntity> statuses = new ArrayList<>();
        for (AccountRequestEntity request : requests) {
            AccountRequestStatusEntity accountRequestStatusEntity = request.getStatuses().get(request.getStatuses().size() - 1);
            statuses.add(accountRequestStatusEntity);
        }
        return statuses;
    }
}
