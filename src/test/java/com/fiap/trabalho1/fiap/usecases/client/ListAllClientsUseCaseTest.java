package com.fiap.trabalho1.fiap.usecases.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fiap.trabalho1.fiap.entities.Client;
import com.fiap.trabalho1.fiap.gateways.ClientRepository;

class ListAllClientsUseCaseTest {

    private ClientRepository clientRepository;
    private ListAllClientsUseCase listAllClientsUseCase;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        listAllClientsUseCase = new ListAllClientsUseCase(clientRepository);
    }

    @Test
    void shouldCallRepositoryWithCorrectPageRequest() {
        int page = 1;
        int size = 12;
        Page<Client> fakePage = new PageImpl<>(Collections.emptyList());
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(fakePage);

        Page<Client> result = listAllClientsUseCase.listAllClients(page, size);

        assertEquals(fakePage, result);
        ArgumentCaptor<PageRequest> captor = ArgumentCaptor.forClass(PageRequest.class);
        verify(clientRepository).findAll(captor.capture());
        PageRequest capturedPageRequest = captor.getValue();
        assertEquals(page, capturedPageRequest.getPageNumber());
        assertEquals(size, capturedPageRequest.getPageSize());
        assertEquals(Sort.Direction.ASC, capturedPageRequest.getSort().getOrderFor("name").getDirection());
        assertEquals("name", capturedPageRequest.getSort().getOrderFor("name").getProperty());
    }

    @Test
    void shouldReturnPageWithClients() {
        int page = 0;
        int size = 2;
        Client client1 = new Client();
        Client client2 = new Client();
        List<Client> clients = Arrays.asList(client1, client2);
        Page<Client> expectedPage = new PageImpl<>(clients, PageRequest.of(page, size, Sort.by("name")), 2);
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<Client> result = listAllClientsUseCase.listAllClients(page, size);

        assertEquals(2, result.getNumberOfElements());
        assertEquals(clients, result.getContent());
    }

    @Test
    void shouldReturnEmptyPageIfNoneFound() {
        when(clientRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        Page<Client> result = listAllClientsUseCase.listAllClients(0, 10);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldPropagateExceptionIfRepositoryFails() {
        when(clientRepository.findAll(any(PageRequest.class))).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> listAllClientsUseCase.listAllClients(0, 10));
        verify(clientRepository, times(1)).findAll(any(PageRequest.class));
    }
}

