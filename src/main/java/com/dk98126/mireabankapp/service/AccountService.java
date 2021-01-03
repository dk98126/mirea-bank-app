package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import com.dk98126.mireabankapp.repository.AccountRepo;
import com.dk98126.mireabankapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;

    public void createAccount() {

    }

    public List<AccountRequestStatusEntity> findALlRequests(String status) {
        switch (status) {
            case "CREATED":
                return accountRepo.findAllRequestsWithCreatedStatus();
            case "IN_PROGRESS":
                return accountRepo.findAllRequestsWithInProgressStatus();
            case "APPROVED":
                return accountRepo.findAllRequestsWithApprovedStatus();
            default:
                return accountRepo.findAllRequestsWithDeclinedStatus();
        }
    }
}
