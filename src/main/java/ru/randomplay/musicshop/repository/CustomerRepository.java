package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c " +
            "JOIN FETCH c.user u " +
            "WHERE u.email = :email")
    Optional<Customer> findByUserEmail(@Param("email") String email);

    @Query("SELECT cs FROM Customer cs " +
            "JOIN FETCH cs.user u " +
            "JOIN FETCH cs.cart cr " +
            "JOIN FETCH cr.cartItems ci " +
            "JOIN FETCH ci.product " +
            "WHERE u.email = :email")
    Optional<Customer> findWithCartAndProductsByUserEmail(@Param("email") String email);
}
