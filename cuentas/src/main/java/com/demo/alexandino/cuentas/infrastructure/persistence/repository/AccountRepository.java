package com.demo.alexandino.cuentas.infrastructure.persistence.repository;

import com.demo.alexandino.cuentas.infrastructure.persistence.entity.AccountPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountPersistence, String> {
    Boolean existsByAccountNumber(String accountNumber);
    Optional<AccountPersistence> findByAccountNumber(String accountNumber);
    Optional<AccountPersistence> findByClientPersistenceName(String name);

}
