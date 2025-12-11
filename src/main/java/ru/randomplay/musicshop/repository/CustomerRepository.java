package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT cs FROM Customer cs " +
            "LEFT JOIN FETCH cs.user u " +
            "LEFT JOIN FETCH cs.cart cr " +
            "LEFT JOIN FETCH cr.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE u.email = :email")
    Optional<Customer> findWithCartAndProductsByUserEmail(@Param("email") String email);
}
