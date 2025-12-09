package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN FETCH e.store " +
            "LEFT JOIN FETCH e.user")
    List<Employee> findAllWithUserAndStore();
}
