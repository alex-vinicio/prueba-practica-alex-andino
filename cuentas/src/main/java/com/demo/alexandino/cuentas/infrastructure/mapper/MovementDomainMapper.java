package com.demo.alexandino.cuentas.infrastructure.mapper;

import com.demo.alexandino.cuentas.domain.Movement;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.MovementPersistence;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementRequest;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementDomainMapper {
    @Mapping(source = "account.clientPersistence.name", target = "account.clientName")
    Movement toDomainPersistence(MovementPersistence persistence);

    MovementPersistence toPersistence(Movement domain);

    Movement toDomain(MovementRequest request);

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    @Mapping(source = "account.accountType", target = "accountType")
    @Mapping(source = "account.status", target = "status")
    @Mapping(source = "value", target = "movement")
    @Mapping(source = "balance", target = "availableBalance")
    @Mapping(source = "account.clientName", target = "client")
    MovementResponse toResponse(Movement domain);
}