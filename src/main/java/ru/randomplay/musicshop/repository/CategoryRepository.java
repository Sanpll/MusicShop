package ru.randomplay.musicshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.randomplay.musicshop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
