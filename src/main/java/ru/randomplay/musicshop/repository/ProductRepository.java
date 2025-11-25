package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
