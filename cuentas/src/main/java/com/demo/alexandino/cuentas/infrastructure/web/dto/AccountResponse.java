package com.demo.alexandino.cuentas.infrastructure.web.dto;

public record AccountResponse(
        String accountNumber,
        String accountType,
        String initialBalance,
        String status)
{}
