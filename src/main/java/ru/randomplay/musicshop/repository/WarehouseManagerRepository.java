package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.WarehouseManager;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseManagerRepository extends JpaRepository<WarehouseManager, Long> {
    @Query("SELECT wm FROM WarehouseManager wm " +
            "LEFT JOIN FETCH wm.store " +
            "LEFT JOIN FETCH wm.user " +
            "WHERE wm.id = :id")
    Optional<WarehouseManager> findByIdWithUserAndStore(@Param("id") Long id);

    @Query("SELECT DISTINCT wm FROM WarehouseManager wm " +
            "LEFT JOIN FETCH wm.store " +
            "LEFT JOIN FETCH wm.user")
    List<WarehouseManager> findAllWithUserAndStore();
}
