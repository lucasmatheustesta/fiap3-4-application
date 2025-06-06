package com.fiap.trabalho1.fiap.controllers;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.external.request.ClientRequest;
import com.fiap.trabalho1.fiap.usecases.client.CreateClientUseCase;
import com.fiap.trabalho1.fiap.usecases.client.ListAllClientsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ListAllClientsUseCase listAllClientsUseCase;
	
	@Autowired
	private CreateClientUseCase createClientUseCase;
	
	@PostMapping
	public ResponseEntity<Client> saveClient(@RequestBody ClientRequest request) {
		Client clientSaved = this.createClientUseCase.execute(request.getName(), request.getCPF(), request.getEmail());
		return ResponseEntity.ok(clientSaved);
	}

	@GetMapping
	public ResponseEntity<Page<Client>> listAllClients(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
													   @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		Page<Client> clients = this.listAllClientsUseCase.listAllClients(page, size);
    	if (clients.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(clients);
	}
	
}
