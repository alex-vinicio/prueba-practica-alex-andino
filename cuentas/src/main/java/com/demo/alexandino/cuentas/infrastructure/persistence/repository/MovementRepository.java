package com.demo.alexandino.cuentas.infrastructure.persistence.repository;

import com.demo.alexandino.cuentas.infrastructure.persistence.entity.MovementPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<MovementPersistence, Long> {
    List<MovementPersistence> findByAccountAccountNumber(String accountNumber);
    
    @Query("SELECT m FROM MovementPersistence m " +
           "WHERE m.date >= :date " +
           "AND m.account.clientPersistence.name = :userName")
    List<MovementPersistence> findByDateAndUserName(
            @Param("date") String date,
            @Param("userName") String userName);
}