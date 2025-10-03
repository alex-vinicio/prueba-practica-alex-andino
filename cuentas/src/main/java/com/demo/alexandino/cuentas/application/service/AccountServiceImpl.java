package com.demo.alexandino.cuentas.application.service;

import com.demo.alexandino.cuentas.application.port.in.AccountInPort;
import com.demo.alexandino.cuentas.application.port.out.AccountOutPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.infrastructure.exception.AccountDuplicatedException;
import com.demo.alexandino.cuentas.infrastructure.exception.AccountNotFoundException;
import com.demo.alexandino.cuentas.infrastructure.mapper.AccountDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.ClientPersistence;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountInPort {
    private static final String DELETE_ACCOUNT_OK = "Delete account OK";
    private static final String ID_ACCOUNT_NOT_FOUND_MESSAGE = "Cuenta con id %s no encontrado!";
    private static final String DUPLICATE_ACCOUNT_MESSAGE = "La cuenta ya existe!";
    private static final String ID_CLIENT_NOT_FOUND_MESSAGE = "Cliente con id %s no encontrado!";

    private final AccountOutPort accountOutPort;
    private final AccountDomainMapper accountDomainMapper;

    public List<Account> getAll() {
        return accountOutPort.getAll().stream().map(accountDomainMapper::toDomain).toList();
    }

    public Account getById(String id) {
        return accountOutPort.getById(id).map(accountDomainMapper::toDomain)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ID_ACCOUNT_NOT_FOUND_MESSAGE,id)));
    }

    public ClientPersistence extractClientEntity(Account account){
        var clientPersistence = accountOutPort.getByClientName(account.getClientName());
        boolean Client = clientPersistence
                .filter(c -> c.getName().equals(account.getClientName()))
                .isPresent();
        if(!Client){
            throw new AccountDuplicatedException(String.format(ID_CLIENT_NOT_FOUND_MESSAGE,account.getClientName()));
        }
        return clientPersistence.get();
    }

    public Account create(Account account) {
        if (getAll().stream().anyMatch(c -> c.getAccountNumber().equals(account.getAccountNumber()))) {
            throw new AccountDuplicatedException(DUPLICATE_ACCOUNT_MESSAGE);
        }
        var accountPersistence = accountDomainMapper.toPersistence(account);
        accountPersistence.setClientPersistence(extractClientEntity(account));
        return accountDomainMapper.toDomain(
                accountOutPort.create(
                        accountPersistence
                )
        );
    }

    public Account update(String id, Account account) {
        return accountOutPort.update(id, accountDomainMapper.toPersistence(account))
                .map(accountDomainMapper::toDomain)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ID_ACCOUNT_NOT_FOUND_MESSAGE,id)));
    }

    public void delete(String id) {
        accountOutPort.delete(accountDomainMapper.toPersistence(getById(id)));
    }
}
