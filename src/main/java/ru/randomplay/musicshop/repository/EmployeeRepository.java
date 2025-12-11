package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.store " +
            "LEFT JOIN FETCH e.user " +
            "WHERE e.id = :id")
    Optional<Employee> findByIdWithUserAndStore(@Param("id") Long id);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.user u " +
            "WHERE u.email = :email")
    Optional<Employee> findByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN FETCH e.store " +
            "LEFT JOIN FETCH e.user")
    List<Employee> findAllWithUserAndStore();
}
