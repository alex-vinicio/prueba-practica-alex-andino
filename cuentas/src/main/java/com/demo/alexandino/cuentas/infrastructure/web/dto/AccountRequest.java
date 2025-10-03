package com.demo.alexandino.cuentas.infrastructure.web.dto;

import com.demo.alexandino.cuentas.domain.enums.AccountType;
import com.demo.alexandino.cuentas.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank(message = "Valor no nulo")
        @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
        String accountNumber,
        @NotNull(message = "El tipo de cuenta es obligatorio")
        AccountType accountType,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 1, max = 10, message = "El saldo inicial debe tener entre 1 y 10 caracteres")

        @NotBlank(message = "Valor no nulo")
        @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
        String initialBalance,

        @NotNull(message = "El estado es obligatorio")

        Status status,

        @NotNull(message = "El campo es obligatorio")
        String clientName
) {
}
