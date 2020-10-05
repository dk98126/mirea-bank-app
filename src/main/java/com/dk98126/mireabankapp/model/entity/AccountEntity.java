package com.dk98126.mireabankapp.model.entity;

import com.dk98126.mireabankapp.model.enm.AccountType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account")
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "frozen", nullable = false, columnDefinition = "boolean default false")
    private boolean frozen;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
