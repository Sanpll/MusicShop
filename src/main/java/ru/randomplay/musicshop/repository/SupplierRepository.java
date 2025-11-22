package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<String> findByName(String name);
}
