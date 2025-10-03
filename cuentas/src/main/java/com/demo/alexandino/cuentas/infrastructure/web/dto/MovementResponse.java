package com.demo.alexandino.cuentas.infrastructure.web.dto;

import com.demo.alexandino.cuentas.domain.enums.AccountType;
import com.demo.alexandino.cuentas.domain.enums.MovementType;

public record MovementResponse(
    String date,
    String client,
    String accountNumber,
    MovementType movementType,
    AccountType accountType,
    Double initialBalance,
    String status,
    Double movement,
    Double availableBalance
) {}