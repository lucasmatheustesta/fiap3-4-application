package com.fiap.trabalho1.fiap.usecases.client;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.gateways.ClientRepository;


@Service
public class CreateClientUseCase {

	private final ClientRepository clientRepository;
	
	public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client execute(String name, String CPF, String email) {
    	Client client = new Client(
                UUID.randomUUID(),
                name,
                CPF,
                email
        );
    	
        return clientRepository.save(client);
    }
    
}
