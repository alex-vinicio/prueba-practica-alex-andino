package com.demo.alexandino.cuentas.application.port.out;

import com.demo.alexandino.cuentas.infrastructure.persistence.entity.AccountPersistence;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.ClientPersistence;

import java.util.List;
import java.util.Optional;

public interface AccountOutPort {
    public List<AccountPersistence> getAll();
    public Optional<AccountPersistence> getById(String id);
    public AccountPersistence create(AccountPersistence client);
    public Optional<AccountPersistence> update(String id, AccountPersistence clientDetails);
    public void delete(AccountPersistence account);
    public Optional<ClientPersistence> getByClientName(String id);
}
