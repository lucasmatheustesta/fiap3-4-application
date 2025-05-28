package com.fiap.trabalho1.fiap.gateways;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.trabalho1.fiap.entities.Category;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Optional<Category> findById(UUID id);
	Page<Category> findAll(Pageable pageable);
    void deleteById(UUID id);
    
}
