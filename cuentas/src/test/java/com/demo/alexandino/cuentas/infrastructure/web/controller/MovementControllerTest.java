package com.demo.alexandino.cuentas.infrastructure.web.controller;

import com.demo.alexandino.cuentas.application.port.in.MovementInPort;
import com.demo.alexandino.cuentas.domain.Account;
import com.demo.alexandino.cuentas.domain.Movement;
import com.demo.alexandino.cuentas.domain.enums.AccountType;
import com.demo.alexandino.cuentas.domain.enums.MovementType;
import com.demo.alexandino.cuentas.infrastructure.mapper.MovementDomainMapper;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementRequest;
import com.demo.alexandino.cuentas.infrastructure.web.dto.MovementResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MovementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovementInPort movementService;

    @Mock
    private MovementDomainMapper mapper;

    @InjectMocks
    private MovementController movementController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(movementController)
                .build();
        Account account = new Account();
        account.setAccountNumber("12345");

        testMovement = new Movement();
        testMovement.setId(1L);
        testMovement.setDate("03/10/2025");
        testMovement.setValue(100.0);
        testMovement.setBalance(500.0);
        testMovement.setMovementType(MovementType.CREDIT);
        testMovement.setAccount(account);

        testMovementResponse = new MovementResponse(
                "03/10/2025",
                "test",
                "12345344",
                MovementType.CREDIT,
                AccountType.AHORRO,
                100.0,
                "OK",
                200.0,
                200.0
        );

        testMovementRequest = new MovementRequest(
                MovementType.CREDIT,
                100.0,
                "12345"
        );
    }

    private Movement testMovement;
    private MovementResponse testMovementResponse;
    private MovementRequest testMovementRequest;

    @Test
    void getMovementsByDateAndUserName_ShouldReturnMovementsList() throws Exception {
        List<Movement> movements = Arrays.asList(testMovement);
        List<MovementResponse> responses = Arrays.asList(testMovementResponse);

        when(movementService.getMovementsByDateAndUserName(anyString(), anyString())).thenReturn(movements);
        when(mapper.toResponse(any(Movement.class))).thenReturn(testMovementResponse);

        mockMvc.perform(get("/api/v1/movements/search")
                .param("date", "03/10/2025")
                .param("userName", "John"))
                .andExpect(status().isOk());

        verify(movementService).getMovementsByDateAndUserName("03/10/2025", "John");
    }

    @Test
    void getMovementById_ShouldReturnMovement() throws Exception {
        when(movementService.getMovementById(1L)).thenReturn(testMovement);
        when(mapper.toResponse(any(Movement.class))).thenReturn(testMovementResponse);

        mockMvc.perform(get("/api/v1/movements/1"))
                .andExpect(status().isOk());

        verify(movementService).getMovementById(1L);
    }

    @Test
    void getAllMovements_ShouldReturnAllMovements() throws Exception {
        List<Movement> movements = Arrays.asList(testMovement);
        List<MovementResponse> responses = Arrays.asList(testMovementResponse);

        when(movementService.getAllMovements()).thenReturn(movements);
        when(mapper.toResponse(any(Movement.class))).thenReturn(testMovementResponse);

        mockMvc.perform(get("/api/v1/movements"))
                .andExpect(status().isOk());

        verify(movementService).getAllMovements();
    }

    @Test
    void deleteMovement_ShouldReturnNoContent() throws Exception {
        doNothing().when(movementService).deleteMovement(1L);

        mockMvc.perform(delete("/api/v1/movements/1"))
                .andExpect(status().isNoContent());

        verify(movementService).deleteMovement(1L);
    }
}