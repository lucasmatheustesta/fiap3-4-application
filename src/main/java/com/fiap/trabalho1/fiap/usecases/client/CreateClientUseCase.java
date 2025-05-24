package com.fiap.trabalho1.fiap.usecases.client;

import java.util.UUID;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.gateways.ClientRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;
import com.fiap.trabalho1.fiap.utils.validators.EmailValidator;

import org.springframework.stereotype.Service;


@Service
public class CreateClientUseCase {

	private final ClientRepository clientRepository;
	
	public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client execute(String name, String cpf, String email) {
    	if (!EmailValidator.validate(email)) {
    		throw new BusinessValidationException("E-mail inválido!");
    	}
    	
    	if (this.clientRepository.findByCPF(cpf).isPresent()) {
    		throw new BusinessValidationException("Cliente com CPF já cadastrado!");
    	}
    	
    	if (this.clientRepository.findByEmail(email).isPresent()) {
    		throw new BusinessValidationException("Cliente com E-mail já cadastrado!");
    	}

    	Client client = new Client(
                UUID.randomUUID(),
                name,
                cpf,
                email
        );
    	
        return clientRepository.save(client);
    }
    
}
