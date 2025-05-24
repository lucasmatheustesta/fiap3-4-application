package com.fiap.trabalho1.fiap.gateways;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fiap.trabalho1.fiap.entities.Order;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    
	Optional<Order> findById(UUID id);
	
	
    void deleteById(UUID id);
    @Query("""
    SELECT o FROM Order o
    WHERE o.status != 'FINISHED'
    ORDER BY 
        CASE o.status
            WHEN 'READY' THEN 1
            WHEN 'PREPARATION' THEN 2
            WHEN 'RECEIVED' THEN 3
            ELSE 4
        END,
        o.orderDate ASC
    """)
    Page<Order> findActiveOrdersOrdered(Pageable pageable);

    
}
