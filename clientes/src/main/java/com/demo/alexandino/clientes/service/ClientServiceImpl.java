package com.demo.alexandino.clientes.service;

import com.demo.alexandino.clientes.domain.Client;
import com.demo.alexandino.clientes.exception.AesGcmFailCryptoException;
import com.demo.alexandino.clientes.repository.ClientRepository;
import com.demo.alexandino.clientes.utils.AesGcmAlgorithm;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Builder
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AesGcmAlgorithm aesGcm;
    private static final String SUCCESSFUL_UPDATE_CLIENT = "Cliente {} actualizado.";

    public ClientServiceImpl(ClientRepository clientRepository, AesGcmAlgorithm aesGcm) {
        this.clientRepository = clientRepository;
        this.aesGcm = aesGcm;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> getById(String id) {
        return clientRepository.findByName(id);
    }

    public Client create(Client client) {
        client.setPassword(encryptPassword(client.getPassword()));
        return clientRepository.save(client);
    }

    private String encryptPassword(String value) {
        try {
            return aesGcm.encrypt(value);
        }catch (Exception ex){
           throw new AesGcmFailCryptoException(ex.getMessage());
        }
    }

    public Optional<Client> update(String id, Client clientDetails) {
        return clientRepository.findByName(id)
                .map(client -> {
                    clientDetails.setClientId(client.getClientId());
                    clientDetails.setPassword(encryptPassword(clientDetails.getPassword()));
                    clientRepository.save(clientDetails);
                    log.info(SUCCESSFUL_UPDATE_CLIENT, id);
                    return clientDetails;
                });
    }

    public boolean delete(String id) {
        return clientRepository.findByName(id).map(client -> {
            clientRepository.delete(client);
            return true;
        }).orElse(false);
    }

}
