package com.demo.alexandino.cuentas.application.service;

import com.demo.alexandino.cuentas.application.port.in.MovementInPort;
import com.demo.alexandino.cuentas.application.port.out.AccountOutPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.domain.Movement;
import com.demo.alexandino.cuentas.domain.enums.MovementType;
import com.demo.alexandino.cuentas.infrastructure.exception.InvalidBalanceException;
import com.demo.alexandino.cuentas.infrastructure.exception.MovementNotFoundException;
import com.demo.alexandino.cuentas.infrastructure.mapper.MovementDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.persistence.repository.MovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovementServiceImpl implements MovementInPort {

    private final MovementRepository movementRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final MovementDomainMapper mapper;
    private final AccountOutPort accountOutPort;

    public MovementServiceImpl(MovementRepository movementRepository, AccountServiceImpl accountServiceImpl, MovementDomainMapper mapper, AccountOutPort accountOutPort) {
        this.movementRepository = movementRepository;
        this.accountServiceImpl = accountServiceImpl;
        this.mapper = mapper;
        this.accountOutPort = accountOutPort;
    }

    @Override
    @Transactional
    public Movement createMovement(Movement movement) {
        var account = accountServiceImpl.getById(movement.getAccount().getAccountNumber());

        Double currentBalance = getCurrentBalance(movement, account);

        movement.setBalance(currentBalance);
        movement.setDate(getDateTime());
        movement.setInitialBalance(Double.parseDouble(account.getInitialBalance()));

        account.setInitialBalance(Double.toString(currentBalance));
        accountServiceImpl.update(account.getAccountNumber(),account);
        movement.setAccount(account);
        var persistence = mapper.toPersistence(movement);
        movementRepository.save(persistence);
        return movement;
    }

    private static String getDateTime() {
        LocalDateTime timeDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return timeDate.format(formatter);
    }

    private static Double getCurrentBalance(Movement movement, Account account) {
        Double currentBalance = Double.parseDouble(account.getInitialBalance());
        if (movement.getMovementType() == MovementType.DEBIT) {
            if (currentBalance < movement.getValue()) {
                throw new InvalidBalanceException("Saldo no disponible!");
            }
            currentBalance -= movement.getValue();
        } else {
            currentBalance += movement.getValue();
        }
        return currentBalance;
    }

    @Override
    public List<Movement> getMovementsByAccountId(String accountId) {
        return movementRepository.findByAccountAccountNumber(accountId).stream()
                .map(mapper::toDomainPersistence)
                .collect(Collectors.toList());
    }

    @Override
    public Movement getMovementById(Long id) {
        return movementRepository.findById(id)
                .map(mapper::toDomainPersistence)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found"));
    }

    @Override
    public List<Movement> getAllMovements() {
        return movementRepository.findAll().stream()
                .map(mapper::toDomainPersistence)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> getMovementsByDateAndUserName(String date, String userName) {
        return movementRepository.findByDateAndUserName(date, userName).stream()
                .map(mapper::toDomainPersistence)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMovement(Long id) {
        if (!movementRepository.existsById(id)) {
            throw new MovementNotFoundException("Movement not found");
        }
        movementRepository.deleteById(id);
    }
}