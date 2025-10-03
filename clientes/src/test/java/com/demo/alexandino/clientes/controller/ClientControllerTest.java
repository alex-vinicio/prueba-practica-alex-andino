package com.demo.alexandino.clientes.controller;

import com.demo.alexandino.clientes.domain.Client;
import com.demo.alexandino.clientes.domain.Gender;
import com.demo.alexandino.clientes.domain.Status;
import com.demo.alexandino.clientes.dto.ClientRequest;
import com.demo.alexandino.clientes.dto.ClientResponse;
import com.demo.alexandino.clientes.exception.ClientDuplicatedException;
import com.demo.alexandino.clientes.exception.ClientNotFoundException;
import com.demo.alexandino.clientes.mapper.ClientDomainMapper;
import com.demo.alexandino.clientes.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientDomainMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    private Client testClient;
    private ClientRequest testClientRequest;

    @BeforeEach
    void setUp() {
        testClient = new Client();
        testClient.setClientId(1L);
        testClient.setIdentifier("1234567890");
        testClient.setName("Test Client");
        testClient.setGender(Gender.HOMBRE);
        testClient.setAge(30);
        testClient.setAddress("Test Address");
        testClient.setPhone("1234567890");
        testClient.setEmail("test@test.com");
        testClient.setPassword("password");
        testClient.setStatus(Status.ACTIVO);

        testClientRequest = new ClientRequest(
            "1234567890",
            "Test Client",
                Gender.HOMBRE
            ,
            30,
            "Test Address",
            "1234567890",
            "password",
            "test@test.com",
            Status.ACTIVO
        );
    }

    @Test
    void whenGetAll_thenReturnClientList() {
        // Given
        List<Client> clients = Arrays.asList(testClient);
        when(clientService.getAll()).thenReturn(clients);
        when(clientMapper.ClientToClientRequest(any(Client.class))).thenReturn(testClientRequest);

        // When
        List<ClientRequest> result = clientController.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clientService).getAll();
        verify(clientMapper).ClientToClientRequest(any(Client.class));
    }

    @Test
    void whenGetById_withValidId_thenReturnClient() {
        // Given
        String id = "1234567890";
        when(clientService.getById(id)).thenReturn(Optional.of(testClient));
        when(clientMapper.ClientToClientRequest(testClient)).thenReturn(testClientRequest);

        // When
        ResponseEntity<ClientRequest> response = clientController.getById(id);

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(testClientRequest, response.getBody());
    }

    @Test
    void whenGetById_withInvalidId_thenThrowException() {
        // Given
        String id = "nonexistent";
        when(clientService.getById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ClientNotFoundException.class, () -> clientController.getById(id));
    }

    @Test
    void whenCreate_withValidClient_thenReturnCreated() {
        // Given
        when(clientService.getAll()).thenReturn(List.of());
        when(clientMapper.clientRequestToClient(testClientRequest)).thenReturn(testClient);
        when(clientService.create(any(Client.class))).thenReturn(testClient);

        // When
        ResponseEntity<ClientResponse> response = clientController.create(testClientRequest);

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().message().contains("Create client OK"));
    }

    @Test
    void whenUpdate_withValidId_thenReturnUpdated() {
        // Given
        String id = "1234567890";
        when(clientMapper.clientRequestToClient(testClientRequest)).thenReturn(testClient);
        when(clientService.update(eq(id), any(Client.class))).thenReturn(Optional.of(testClient));
        when(clientMapper.ClientToClientRequest(testClient)).thenReturn(testClientRequest);

        // When
        ResponseEntity<ClientRequest> response = clientController.update(id, testClientRequest);

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(testClientRequest, response.getBody());
    }

    @Test
    void whenUpdate_withInvalidId_thenThrowException() {
        // Given
        String id = "nonexistent";
        when(clientMapper.clientRequestToClient(testClientRequest)).thenReturn(testClient);
        when(clientService.update(eq(id), any(Client.class))).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ClientNotFoundException.class, () -> clientController.update(id, testClientRequest));
    }

    @Test
    void whenDelete_withValidId_thenReturnSuccess() {
        // Given
        String id = "1234567890";
        when(clientService.delete(id)).thenReturn(true);

        // When
        ResponseEntity<ClientResponse> response = clientController.delete(id);

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().message().contains("Delete client OK"));
    }

    @Test
    void whenDelete_withInvalidId_thenThrowException() {
        // Given
        String id = "nonexistent";
        when(clientService.delete(id)).thenReturn(false);

        // When/Then
        assertThrows(ClientNotFoundException.class, () -> clientController.delete(id));
    }
}