package com.dk98126.mireabankapp.model.entity;

import com.dk98126.mireabankapp.model.enm.AccountType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "account_request")
@NoArgsConstructor
public class AccountRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_req_id_gen")
    @SequenceGenerator(name = "acc_req_id_gen", sequenceName = "acc_req_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "request")
    private List<AccountRequestStatusEntity> statuses;
}
