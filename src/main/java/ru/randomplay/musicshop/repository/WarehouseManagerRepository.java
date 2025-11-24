package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.WarehouseManager;

@Repository
public interface WarehouseManagerRepository extends JpaRepository<WarehouseManager, Long> {
}
