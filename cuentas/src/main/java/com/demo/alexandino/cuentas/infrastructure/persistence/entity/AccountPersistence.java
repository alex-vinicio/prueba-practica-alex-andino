package com.demo.alexandino.cuentas.infrastructure.persistence.entity;

import com.demo.alexandino.cuentas.domain.enums.AccountType;
import com.demo.alexandino.cuentas.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class AccountPersistence {
    @Id
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String initialBalance;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientPersistence clientPersistence;
}
