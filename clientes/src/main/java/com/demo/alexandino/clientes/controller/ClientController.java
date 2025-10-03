package com.demo.alexandino.clientes.controller;

import com.demo.alexandino.clientes.dto.ClientRequest;
import com.demo.alexandino.clientes.dto.ClientResponse;
import com.demo.alexandino.clientes.exception.ClientDuplicatedException;
import com.demo.alexandino.clientes.exception.ClientNotFoundException;
import com.demo.alexandino.clientes.mapper.ClientDomainMapper;
import com.demo.alexandino.clientes.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clients")
@Slf4j
public class ClientController {
    private static final String DELETE_CLIENT_OK = "Delete client OK";
    private static final String CREATED_CLIENT_OK = "Create client OK";
    private static final String ID_CLIENT_NOT_FOUND_MESSAGE = "Cliente con id %s no encontrado!";
    private static final String DUPLICATE_CLIENT_MESSAGE = "El cliente ya existe!";
    private final ClientService clientService;
    private final ClientDomainMapper clientMapper;
    public ClientController(ClientService clientService, ClientDomainMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping
    public List<ClientRequest> getAll() {
        return clientService.getAll().stream().map(clientMapper::ClientToClientRequest).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRequest> getById(@PathVariable String id) {
        return clientService.getById(id).map(clientMapper::ClientToClientRequest)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClientNotFoundException(String.format(ID_CLIENT_NOT_FOUND_MESSAGE, id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Validated @RequestBody ClientRequest client) {
        if (clientService.getAll().stream().anyMatch(c -> c.getName().equals(client.name()))) {
            throw new ClientDuplicatedException(DUPLICATE_CLIENT_MESSAGE);
        }
        ClientResponse response = new ClientResponse(CREATED_CLIENT_OK,"ID: "+clientService.create(clientMapper.clientRequestToClient(client)).getIdentifier());
        log.info("{} {}", response.message(), response.title());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientRequest> update(@PathVariable String id, @Validated @RequestBody ClientRequest client) {
        return clientService.update(id, clientMapper.clientRequestToClient(client))
                .map(clientMapper::ClientToClientRequest)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClientNotFoundException(String.format(ID_CLIENT_NOT_FOUND_MESSAGE, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResponse> delete(@PathVariable String id) {
        if(!clientService.delete(id)) throw new ClientNotFoundException(String.format(ID_CLIENT_NOT_FOUND_MESSAGE, id));
        ClientResponse response = new ClientResponse(DELETE_CLIENT_OK, "ID: "+ id );
        log.info("{} {}", response.message(), response.title());
        return ResponseEntity.ok().body(response);
    }
}
