package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
