package com.demo.alexandino.cuentas.infrastructure.web.controller;

import com.demo.alexandino.cuentas.application.port.in.AccountInPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.infrastructure.mapper.AccountDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.web.dto.AccountRequest;
import com.demo.alexandino.cuentas.infrastructure.web.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@CrossOrigin(origins = "*")
public class AccountController {
    private static final String DELETE_ACCOUNT_OK = "Delete account OK";
    private static final String CREATED_ACCOUNT_OK = "Create account OK";
    private final AccountInPort accountInPort;
    private final AccountDomainMapper accountDomainMapper;

    public AccountController(AccountInPort accountInPort, AccountDomainMapper accountDomainMapper) {
        this.accountInPort = accountInPort;
        this.accountDomainMapper = accountDomainMapper;
    }

    @GetMapping
    public List<AccountRequest> getAll() {
        return accountInPort.getAll().stream().map(accountDomainMapper::accountToAccountRequest).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRequest> getById(@PathVariable String id) {
        return ResponseEntity.ok(accountDomainMapper.accountToAccountRequest(accountInPort.getById(id)));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        Account account = accountDomainMapper.accountRequestToAccount(request);
        Account created = accountInPort.create(account);

        AccountResponse response = accountDomainMapper.toAccountResponse(created);
        log.info("Account created: {}", response.accountNumber());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountRequest> update(@PathVariable String id, @Validated @RequestBody AccountRequest request) {
        Account account = accountDomainMapper.accountRequestToAccount(request);
        Account updated = accountInPort.update(id, account);

        AccountRequest response = accountDomainMapper.accountToAccountRequest(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accountInPort.delete(id);
        log.info("Account deleted: {}", id);
        return ResponseEntity.noContent().build();
    }
}
