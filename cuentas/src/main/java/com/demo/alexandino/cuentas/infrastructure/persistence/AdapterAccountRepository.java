package com.demo.alexandino.cuentas.infrastructure.persistence;

import com.demo.alexandino.cuentas.application.port.out.AccountOutPort;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.AccountPersistence;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.ClientPersistence;
import com.demo.alexandino.cuentas.infrastructure.persistence.repository.AccountRepository;
import com.demo.alexandino.cuentas.infrastructure.persistence.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdapterAccountRepository implements AccountOutPort {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AdapterAccountRepository(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<AccountPersistence> getAll() {
        return accountRepository.findAll();
    }

    public Optional<AccountPersistence> getById(String id) {
        return accountRepository.findByAccountNumber(id);
    }

    public Optional<ClientPersistence> getByClientName(String id) {
        return clientRepository.findByName(id);
    }

    public AccountPersistence create(AccountPersistence account) {
        return accountRepository.save(account);
    }

    public Optional<AccountPersistence> update(String id, AccountPersistence account) {
        return accountRepository.findByAccountNumber(id)
                .map(existingAccount -> {
                    account.setClientPersistence(existingAccount.getClientPersistence());
                    account.setAccountType(existingAccount.getAccountType());
                    account.setStatus(existingAccount.getStatus());
                    return accountRepository.save(account);
                });
    }

    public void delete(AccountPersistence account) {
        accountRepository.delete(account);
    }
}
