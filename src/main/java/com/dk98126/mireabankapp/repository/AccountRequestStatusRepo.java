package com.dk98126.mireabankapp.repository;

import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRequestStatusRepo extends JpaRepository<AccountRequestStatusEntity, Long> {
    // TODO решить (?) проблему (?) декартова произведения
    @Query(value = "select ars from AccountRequestStatusEntity ars " +
            "join fetch ars.request ar " +
            "join fetch ar.user u " +
            "where u.login = :login " +
            "order by ars.transitionTime desc")
    List<AccountRequestStatusEntity> findAllUserStatuses(@Param("login") String login);
}
