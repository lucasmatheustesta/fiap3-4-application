package com.fiap.trabalho1.fiap.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client  {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String CPF;

    public Client() {}

    public Client(UUID idClient, String name, String CPF, String email) {
    	this.id = idClient;
    	this.name = name;
    	this.CPF = CPF;
        this.email = email;
    }

    public UUID getIdClient() {
        return id;
    }

    public void setIdClient(UUID idClient) {
        this.id = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
