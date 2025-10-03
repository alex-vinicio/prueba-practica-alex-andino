package com.demo.alexandino.clientes.service;

import com.demo.alexandino.clientes.domain.Client;
import com.demo.alexandino.clientes.domain.Gender;
import com.demo.alexandino.clientes.domain.Status;
import com.demo.alexandino.clientes.exception.AesGcmFailCryptoException;
import com.demo.alexandino.clientes.repository.ClientRepository;
import com.demo.alexandino.clientes.utils.AesGcmAlgorithm;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AesGcmAlgorithm aesGcm;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client testClient;

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
    }

    @Test
    void whenGetAll_thenReturnClientList() {
        // Given
        List<Client> expectedClients = Arrays.asList(testClient);
        when(clientRepository.findAll()).thenReturn(expectedClients);

        // When
        List<Client> actualClients = clientService.getAll();

        // Then
        assertNotNull(actualClients);
        assertEquals(expectedClients.size(), actualClients.size());
        assertEquals(expectedClients.get(0).getName(), actualClients.get(0).getName());
        verify(clientRepository).findAll();
    }

    @Test
    void whenGetById_withValidId_thenReturnClient() {
        // Given
        String id = "Test Client";
        when(clientRepository.findByName(id)).thenReturn(Optional.of(testClient));

        // When
        Optional<Client> result = clientService.getById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testClient.getName(), result.get().getName());
        verify(clientRepository).findByName(id);
    }

    @Test
    void whenGetById_withInvalidId_thenReturnEmpty() {
        // Given
        String id = "nonexistent";
        when(clientRepository.findByName(id)).thenReturn(Optional.empty());

        // When
        Optional<Client> result = clientService.getById(id);

        // Then
        assertTrue(result.isEmpty());
        verify(clientRepository).findByName(id);
    }

    @Test
    void whenCreate_withEncryptionError_thenThrowException() throws Exception {
        // Given
        when(aesGcm.encrypt(anyString())).thenThrow(new Exception("Encryption failed"));

        // When/Then
        assertThrows(AesGcmFailCryptoException.class, () -> clientService.create(testClient));
        verify(aesGcm).encrypt(testClient.getPassword());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void whenUpdate_withInvalidId_thenReturnEmpty() {
        // Given
        String id = "nonexistent";
        when(clientRepository.findByName(id)).thenReturn(Optional.empty());

        // When
        Optional<Client> result = clientService.update(id, testClient);

        // Then
        assertTrue(result.isEmpty());
        verify(clientRepository).findByName(id);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void whenDelete_withValidId_thenReturnTrue() {
        // Given
        String id = "Test Client";
        when(clientRepository.findByName(id)).thenReturn(Optional.of(testClient));
        doNothing().when(clientRepository).delete(any(Client.class));

        // When
        boolean result = clientService.delete(id);

        // Then
        assertTrue(result);
        verify(clientRepository).findByName(id);
        verify(clientRepository).delete(testClient);
    }

    @Test
    void whenDelete_withInvalidId_thenReturnFalse() {
        // Given
        String id = "nonexistent";
        when(clientRepository.findByName(id)).thenReturn(Optional.empty());

        // When
        boolean result = clientService.delete(id);

        // Then
        assertFalse(result);
        verify(clientRepository).findByName(id);
        verify(clientRepository, never()).delete(any(Client.class));
    }
}