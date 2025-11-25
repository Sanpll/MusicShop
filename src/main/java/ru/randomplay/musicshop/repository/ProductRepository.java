package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    // делаем кастомный запрос, чтобы избиваться от N+1
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.categoryLinks cl " +
            "LEFT JOIN FETCH cl.category")
    List<Product> findAllWithCategories();
}
