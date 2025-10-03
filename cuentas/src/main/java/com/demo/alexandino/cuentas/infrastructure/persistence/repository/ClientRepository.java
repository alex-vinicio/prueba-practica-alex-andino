package com.demo.alexandino.cuentas.infrastructure.persistence.repository;

import com.demo.alexandino.cuentas.infrastructure.persistence.entity.ClientPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientPersistence, Long> {
    Optional<ClientPersistence> findByName(String name);
}
