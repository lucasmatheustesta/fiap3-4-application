package com.fiap.trabalho1.fiap.gateways;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.trabalho1.fiap.entities.Client;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, UUID> {

	Optional<Client> findById(UUID id);
	Page<Client> findAll(Pageable pageable);
    void deleteById(UUID id);
    
}
