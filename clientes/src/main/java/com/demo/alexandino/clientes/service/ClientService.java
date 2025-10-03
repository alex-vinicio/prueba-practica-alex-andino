package com.demo.alexandino.clientes.service;

import com.demo.alexandino.clientes.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public List<Client> getAll();
    public Optional<Client> getById(String id);
    public Client create(Client client);
    public Optional<Client> update(String id, Client clientDetails);
    public boolean delete(String id);
}

