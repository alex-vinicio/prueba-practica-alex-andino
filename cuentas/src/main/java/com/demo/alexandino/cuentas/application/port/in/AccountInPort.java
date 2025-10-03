package com.demo.alexandino.cuentas.application.port.in;

import com.demo.alexandino.cuentas.domain.Account;

import java.util.List;

public interface AccountInPort {
    public List<Account> getAll();
    public Account getById(String id);
    public Account create(Account client);
    public Account update(String id, Account clientDetails);
    public void delete(String id);
}
