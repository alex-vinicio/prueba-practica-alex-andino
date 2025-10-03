package com.demo.alexandino.cuentas.application.service;

import com.demo.alexandino.cuentas.application.port.out.AccountOutPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.domain.Movement;
import com.demo.alexandino.cuentas.domain.enums.MovementType;
import com.demo.alexandino.cuentas.infrastructure.exception.InvalidBalanceException;
import com.demo.alexandino.cuentas.infrastructure.exception.MovementNotFoundException;
import com.demo.alexandino.cuentas.infrastructure.mapper.MovementDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.persistence.entity.MovementPersistence;
import com.demo.alexandino.cuentas.infrastructure.persistence.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private MovementDomainMapper mapper;

    @Mock
    private AccountOutPort accountOutPort;

    @InjectMocks
    private MovementServiceImpl movementService;

    private Movement testMovement;
    private Account testAccount;
    private MovementPersistence testMovementPersistence;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setAccountNumber("12345");
        testAccount.setInitialBalance("1000.0");

        testMovement = new Movement();
        testMovement.setId(1L);
        testMovement.setDate("03/10/2025");
        testMovement.setValue(100.0);
        testMovement.setBalance(1100.0);
        testMovement.setMovementType(MovementType.CREDIT);
        testMovement.setAccount(testAccount);

        testMovementPersistence = new MovementPersistence();
        // Set persistence properties as needed
    }

    @Test
    void createMovement_WithValidCreditMovement_ShouldSucceed() {
        when(accountService.getById(anyString())).thenReturn(testAccount);
        when(mapper.toPersistence(any(Movement.class))).thenReturn(testMovementPersistence);
        when(movementRepository.save(any(MovementPersistence.class))).thenReturn(testMovementPersistence);

        Movement result = movementService.createMovement(testMovement);

        assertNotNull(result);
        assertEquals(1100.0, result.getBalance());
        verify(accountService).update(anyString(), any(Account.class));
    }

    @Test
    void createMovement_WithInsufficientBalance_ShouldThrowException() {
        testMovement.setMovementType(MovementType.DEBIT);
        testMovement.setValue(2000.0);

        when(accountService.getById(anyString())).thenReturn(testAccount);

        assertThrows(InvalidBalanceException.class, () -> 
            movementService.createMovement(testMovement)
        );
    }

    @Test
    void getMovementsByDateAndUserName_ShouldReturnList() {
        List<MovementPersistence> persistenceList = Arrays.asList(testMovementPersistence);
        when(movementRepository.findByDateAndUserName(anyString(), anyString()))
            .thenReturn(persistenceList);
        when(mapper.toDomainPersistence(any(MovementPersistence.class)))
            .thenReturn(testMovement);

        List<Movement> result = movementService.getMovementsByDateAndUserName("03/10/2025", "John");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getMovementById_WhenExists_ShouldReturnMovement() {
        when(movementRepository.findById(1L)).thenReturn(Optional.of(testMovementPersistence));
        when(mapper.toDomainPersistence(any(MovementPersistence.class))).thenReturn(testMovement);

        Movement result = movementService.getMovementById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getMovementById_WhenNotExists_ShouldThrowException() {
        when(movementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovementNotFoundException.class, () -> 
            movementService.getMovementById(1L)
        );
    }

    @Test
    void getAllMovements_ShouldReturnList() {
        List<MovementPersistence> persistenceList = Arrays.asList(testMovementPersistence);
        when(movementRepository.findAll()).thenReturn(persistenceList);
        when(mapper.toDomainPersistence(any(MovementPersistence.class))).thenReturn(testMovement);

        List<Movement> result = movementService.getAllMovements();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void deleteMovement_WhenExists_ShouldDelete() {
        when(movementRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movementRepository).deleteById(1L);

        assertDoesNotThrow(() -> movementService.deleteMovement(1L));
        verify(movementRepository).deleteById(1L);
    }

    @Test
    void deleteMovement_WhenNotExists_ShouldThrowException() {
        when(movementRepository.existsById(1L)).thenReturn(false);

        assertThrows(MovementNotFoundException.class, () -> 
            movementService.deleteMovement(1L)
        );
        verify(movementRepository, never()).deleteById(any());
    }
}