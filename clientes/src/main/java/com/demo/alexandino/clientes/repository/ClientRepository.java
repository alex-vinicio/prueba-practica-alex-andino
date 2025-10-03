package com.demo.alexandino.clientes.repository;

import com.demo.alexandino.clientes.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByIdentifier(String identifier);
    Optional<Client> findByIdentifier(String identifier);
    Optional<Client> findByName(String name);
}
