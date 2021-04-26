package com.wooridreamcardream.repository;

import com.wooridreamcardream.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
