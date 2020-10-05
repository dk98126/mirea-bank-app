package com.dk98126.mireabankapp.model.entity;

import com.dk98126.mireabankapp.model.enm.AccountRequestTransitionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "account_request_status")
@NoArgsConstructor
public class AccountRequestTransitionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "transition_time", nullable = false)
    private LocalDateTime transitionTime;

    @Column(name = "status", nullable = false)
    private AccountRequestTransitionStatus status;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_request_id", referencedColumnName = "id", nullable = false)
    private AccountRequestEntity request;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity manager;
}
