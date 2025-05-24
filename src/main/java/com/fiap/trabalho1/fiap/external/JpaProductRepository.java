//package com.fiap.trabalho1.fiap.external;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.stereotype.Repository;
//
//import com.fiap.trabalho1.fiap.entities.Product;
//import com.fiap.trabalho1.fiap.gateways.ProductRepository;
//
//@Repository
//public class JpaProductRepository implements ProductRepository {
//    private final SpringDataProductRepository repository;
//
//    public JpaProductRepository(SpringDataProductRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public Product save(Product product) {
//        return repository.save(product);
//    }
//
//    @Override
//    public Product findById(UUID id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Product> findAll() {
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
//interface SpringDataProductRepository extends org.springframework.data.jpa.repository.JpaRepository<ProductEntity, UUID> {}