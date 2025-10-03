package com.demo.alexandino.cuentas.infrastructure.persistence.entity;

import com.demo.alexandino.cuentas.domain.enums.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movements")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MovementPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String date;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @Column(nullable = false)
    private Double initialBalance;

    @Column(nullable = false)
    private Double value;
    
    @Column(nullable = false)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_number", nullable = false)
    private AccountPersistence account;

}