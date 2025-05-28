package com.fiap.trabalho1.fiap.usecases.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.gateways.ClientRepository;

class CreateClientUseCaseTest {

    private ClientRepository clientRepository;
    private CreateClientUseCase createClientUseCase;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        createClientUseCase = new CreateClientUseCase(clientRepository);
    }

    @Test
    void shouldCreateClientAndSave() {
        String name = "Maria";
        String cpf = "12345678900";
        String email = "maria@email.com";
        Client savedClient = new Client(null, name, cpf, email);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = createClientUseCase.execute(name, cpf, email);


        assertNotNull(result);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void shouldPassCorrectParametersToClientConstructor() {
        String name = "João";
        String cpf = "09876543211";
        String email = "joao@email.com";
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        createClientUseCase.execute(name, cpf, email);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        Client clientPassed = captor.getValue();

        assertEquals(name, clientPassed.getName());
        assertEquals(cpf, clientPassed.getCPF());
        assertEquals(email, clientPassed.getEmail());
        assertNotNull(clientPassed.getIdClient());
    }

    @Test
    void shouldPropagateExceptionWhenRepositoryFails() {
        when(clientRepository.save(any(Client.class))).thenThrow(new RuntimeException("DB Error"));


        assertThrows(RuntimeException.class, () -> createClientUseCase.execute("José", "45198224700", "jose@email.com"));
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}
