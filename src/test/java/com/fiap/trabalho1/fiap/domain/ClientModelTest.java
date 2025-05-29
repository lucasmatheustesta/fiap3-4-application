package com.fiap.trabalho1.fiap.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ClientModelTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClientModel() {
        ClientModel client = new ClientModel();
        client.setIdClient(UUID.randomUUID());
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");


        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);


        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameNotBlank() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName("");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("O nome é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailValid() {
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("joao.silva");
        client.setCPF("37589572810");

        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("O e-mail deve ser válido", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailNotBlank() {
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("");
        client.setCPF("37589572810");


        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);


        assertFalse(violations.isEmpty());
        assertEquals("O e-mail é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void testCPFValid() {
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("12345678900");

        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("O CPF é inválido", violations.iterator().next().getMessage());
    }


    @Test
    void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        ClientModel client = new ClientModel();
        client.setIdClient(id);
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        
        assertEquals(id, client.getIdClient());
        assertEquals("João Silva", client.getName());
        assertEquals("joao.silva@example.com", client.getEmail());
        assertEquals("37589572810", client.getCPF());
    }

    @Test
    void testSerializable() {
        ClientModel client = new ClientModel();
        client.setIdClient(UUID.randomUUID());
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        assertDoesNotThrow(() -> {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(client);
            oos.close();

            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            ClientModel deserializedClient = (ClientModel) ois.readObject();
            ois.close();

            assertEquals(client.getIdClient(), deserializedClient.getIdClient());
            assertEquals(client.getName(), deserializedClient.getName());
            assertEquals(client.getEmail(), deserializedClient.getEmail());
            assertEquals(client.getCPF(), deserializedClient.getCPF());
        });
    }
}
