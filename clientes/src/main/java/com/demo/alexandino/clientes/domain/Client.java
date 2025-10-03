package com.demo.alexandino.clientes.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "client")
@AllArgsConstructor
@RequiredArgsConstructor
public class Client extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
}