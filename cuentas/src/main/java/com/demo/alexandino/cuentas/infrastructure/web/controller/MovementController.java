package com.demo.alexandino.cuentas.infrastructure.web.controller;

import com.demo.alexandino.cuentas.application.port.in.MovementInPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.infrastructure.mapper.MovementDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementRequest;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movements")
@CrossOrigin(origins = "*")
public class MovementController {

    private final MovementInPort movementService;
    private final MovementDomainMapper mapper;

    public MovementController(MovementInPort movementService, MovementDomainMapper mapper) {
        this.movementService = movementService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<MovementResponse> createMovement(@Valid @RequestBody MovementRequest request) {
        var movement = mapper.toDomain(request);
        movement.setAccount(new Account(request.accountNumber()));
        var created = movementService.createMovement(movement);
        return new ResponseEntity<>(mapper.toResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<MovementResponse>> getMovementsByAccountId(@PathVariable String accountId) {
        var movements = movementService.getMovementsByAccountId(accountId);
        var response = movements.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementResponse> getMovementById(@PathVariable Long id) {
        var movement = movementService.getMovementById(id);
        return ResponseEntity.ok(mapper.toResponse(movement));
    }

    @GetMapping
    public ResponseEntity<List<MovementResponse>> getAllMovements() {
        var movements = movementService.getAllMovements();
        var response = movements.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovementResponse>> getMovementsByDateAndUserName(
            @RequestParam("date") String date,
            @RequestParam("userName") String userName) {
        var movements = movementService.getMovementsByDateAndUserName(date, userName);
        var response = movements.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }
}