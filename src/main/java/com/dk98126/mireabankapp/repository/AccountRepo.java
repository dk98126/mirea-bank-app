package com.dk98126.mireabankapp.repository;

import com.dk98126.mireabankapp.model.entity.AccountEntity;
import com.dk98126.mireabankapp.model.entity.AccountRequestStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

 @Query(value = "select ars from AccountRequestStatusEntity ars " +
         "join fetch ars.request ar " +
         "where ars.status= 'CREATED' " +
         "order by ars.transitionTime desc")
    List<AccountRequestStatusEntity> findAllRequestsWithCreatedStatus();

    @Query(value = "select ars from AccountRequestStatusEntity ars " +
            "join fetch ars.request ar " +
            "where ars.status= 'IN_PROGRESS' " +
            "order by ars.transitionTime desc")
    List<AccountRequestStatusEntity> findAllRequestsWithInProgressStatus();

    @Query(value = "select ars from AccountRequestStatusEntity ars " +
            "join fetch ars.request ar " +
            "where ars.status= 'APPROVED' " +
            "order by ars.transitionTime desc")
    List<AccountRequestStatusEntity> findAllRequestsWithApprovedStatus();

    @Query(value = "select ars from AccountRequestStatusEntity ars " +
            "join fetch ars.request ar " +
            "where ars.status= 'DECLINED' " +
            "order by ars.transitionTime desc")
    List<AccountRequestStatusEntity> findAllRequestsWithDeclinedStatus();
}
