package com.fiap.trabalho1.fiap.usecases.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.gateways.ClientRepository;


@Service
public class ListAllClientsUseCase {

	private final ClientRepository clientRepository;

    public ListAllClientsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public Page<Client> listAllClients(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
		return this.clientRepository.findAll(pageRequest);
	}
    
}
