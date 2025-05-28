package com.fiap.trabalho1.fiap.gateways;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.trabalho1.fiap.entities.Product;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, UUID> {
    
	Optional<Product> findById(UUID id);
	Page<Product> findAll(Pageable pageable);
    void deleteById(UUID id);
    
}
