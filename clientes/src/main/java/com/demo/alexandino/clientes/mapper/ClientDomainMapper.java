package com.demo.alexandino.clientes.mapper;

import com.demo.alexandino.clientes.domain.Client;
import com.demo.alexandino.clientes.dto.ClientRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientDomainMapper {
    Client clientRequestToClient(ClientRequest clientRequest);
    ClientRequest ClientToClientRequest(Client client);
}
