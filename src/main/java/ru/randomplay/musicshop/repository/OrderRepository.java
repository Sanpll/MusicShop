package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product")
    List<Order> findAllWithUserAndProducts();

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer cs " +
            "LEFT JOIN FETCH cs.user " +
            "LEFT JOIN FETCH o.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE o.employee IS NULL")
    List<Order> findActiveWithUserAndProducts();
}
