package com.demo.alexandino.cuentas.infrastructure.web.dto;

import com.demo.alexandino.cuentas.domain.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovementRequest(
    @NotNull(message = "Movement type is required")
    MovementType movementType,

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    Double value,

    @NotNull
    String accountNumber
) {}