package com.demo.alexandino.cuentas.domain;

import com.demo.alexandino.cuentas.domain.enums.AccountType;
import com.demo.alexandino.cuentas.domain.enums.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class Account {
    private String accountNumber;
    private AccountType accountType;
    private String initialBalance;
    private Status status;
    private String clientName;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
