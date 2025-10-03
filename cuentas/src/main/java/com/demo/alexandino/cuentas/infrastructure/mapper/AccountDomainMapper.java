package com.demo.alexandino.cuentas.infrastructure.mapper;

import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.domain.Movement;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.AccountPersistence;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.MovementPersistence;
import com.demo.alexandino.cuentas.infrastructure.web.dto.AccountRequest;
import com.demo.alexandino.cuentas.infrastructure.web.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDomainMapper {
    Account accountRequestToAccount(AccountRequest accountRequest);
    AccountRequest accountToAccountRequest(Account account);

    @Mapping(source = "clientPersistence.name", target = "clientName")
    Account toDomain(AccountPersistence accountPersistence);

    AccountPersistence toPersistence(Account account);
    AccountResponse toAccountResponse(Account account);

}
