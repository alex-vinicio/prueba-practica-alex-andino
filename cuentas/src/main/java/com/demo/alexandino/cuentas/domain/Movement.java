package com.demo.alexandino.cuentas.domain;

import com.demo.alexandino.cuentas.domain.enums.MovementType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Movement {
    private Long id;
    private String date;
    private MovementType movementType;
    private Double initialBalance;
    private Double value;
    private Double balance;
    private Account account;
}