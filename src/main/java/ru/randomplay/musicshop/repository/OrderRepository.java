package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user " +
            "LEFT JOIN FETCH o.employee e " +
            "LEFT JOIN FETCH e.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithUserAndProducts(@Param("id") Long id);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user " +
            "LEFT JOIN FETCH o.employee e " +
            "LEFT JOIN FETCH e.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product")
    List<Order> findAllWithUserAndProducts();

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user " +
            "LEFT JOIN FETCH o.employee e " +
            "LEFT JOIN FETCH e.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE o.employee IS NULL")
    List<Order> findAllWithUserAndProductsAndNullEmployee();

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user u " +
            "LEFT JOIN FETCH o.employee e " +
            "LEFT JOIN FETCH e.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE u.email = :email AND " +
            "o.employee IS NOT NULL")
    List<Order> findAllByEmailWithProducts(@Param("email") String email);
}
