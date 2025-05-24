//package com.fiap.trabalho1.fiap.external;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.stereotype.Repository;
//
//import com.fiap.trabalho1.fiap.entities.Order;
//import com.fiap.trabalho1.fiap.gateways.OrderRepository;
//
//@Repository
//public class JpaOrderRepository implements OrderRepository {
//    private final SpringDataOrderRepository repository;
//
//    public JpaOrderRepository(SpringDataOrderRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public Order save(Order order) {
//        return repository.save(order);
//    }
//
//    @Override
//    public Order findById(UUID id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Order> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public void deleteById(UUID id) {
//        repository.deleteById(id);
//    }
//}
//
//// Interface do Spring Data JPA
//interface SpringDataOrderRepository extends org.springframework.data.jpa.repository.JpaRepository<OrderEntity, UUID> {}