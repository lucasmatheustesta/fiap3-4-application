package com.fiap.trabalho1.fiap.external.request;

import java.util.UUID;

public class ClientRequest {

	private UUID idClient;
	private String name;
    private String CPF;
	private String email;
    
	public UUID getIdClient() {
		return idClient;
	}
	
	public void setIdClient(UUID idClient) {
		this.idClient = idClient;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
    
}
