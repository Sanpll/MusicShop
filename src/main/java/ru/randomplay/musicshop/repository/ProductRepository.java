package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.model.ProductStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.categoryLinks cl " +
            "LEFT JOIN FETCH cl.category " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithCategories(@Param("id") Long id);

    // делаем кастомный запрос, чтобы избиваться от N+1
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.categoryLinks cl " +
            "LEFT JOIN FETCH cl.category " +
            "LEFT JOIN FETCH p.supplier")
    List<Product> findAllWithCategoriesAndSupplier();

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.categoryLinks cl " +
            "LEFT JOIN FETCH cl.category " +
            "LEFT JOIN FETCH p.supplier " +
            "WHERE p.status = :status")
    List<Product> findAllWithCategoriesAndSupplierByStatus(@Param("status") ProductStatus status);
}
