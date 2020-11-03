package com.dk98126.mireabankapp.repository;

import com.dk98126.mireabankapp.model.entity.AccountRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRequestRepo extends JpaRepository<AccountRequestEntity, Long> {

}
