package com.demo.alexandino.cuentas.infrastructure.persistence.entity;

import com.demo.alexandino.cuentas.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientPersistence extends PersonPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
}