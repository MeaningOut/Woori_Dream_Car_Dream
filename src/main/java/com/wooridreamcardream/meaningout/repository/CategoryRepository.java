package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String Name);
}
