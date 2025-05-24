package com.fiap.trabalho1.fiap.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientModelTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClientModel() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setIdClient(UUID.randomUUID());
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810"); // Use um CPF válido para o teste

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);



        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameNotBlank() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName(""); // Nome em branco
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("O nome é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailValid() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("joao.silva"); // Email inválido
        client.setCPF("37589572810");

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("O e-mail deve ser válido", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailNotBlank() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail(""); // Email em branco
        client.setCPF("37589572810");

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("O e-mail é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void testCPFValid() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("12345678900"); // CPF inválido para o caso

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("O CPF é inválido", violations.iterator().next().getMessage());
    }

    @Test
    void testCPFNotBlank() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF(""); // CPF em branco

        // Act
        Set<ConstraintViolation<ClientModel>> violations = validator.validate(client);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("O CPF é inválido", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        ClientModel client = new ClientModel();
        client.setIdClient(id);
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        // Assert
        assertEquals(id, client.getIdClient());
        assertEquals("João Silva", client.getName());
        assertEquals("joao.silva@example.com", client.getEmail());
        assertEquals("37589572810", client.getCPF());
    }

    @Test
    void testSerializable() {
        // Arrange
        ClientModel client = new ClientModel();
        client.setIdClient(UUID.randomUUID());
        client.setName("João Silva");
        client.setEmail("joao.silva@example.com");
        client.setCPF("37589572810");

        // Act & Assert
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
