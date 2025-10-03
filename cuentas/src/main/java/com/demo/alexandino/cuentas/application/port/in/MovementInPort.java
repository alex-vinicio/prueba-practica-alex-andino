package com.demo.alexandino.cuentas.application.port.in;

import com.demo.alexandino.cuentas.domain.Movement;
import java.util.List;

public interface MovementInPort {
    Movement createMovement(Movement movement);
    Movement getMovementById(Long id);
    List<Movement> getAllMovements();
    List<Movement> getMovementsByAccountId(String accountId);
    List<Movement> getMovementsByDateAndUserName(String date, String userName);
    void deleteMovement(Long id);
}