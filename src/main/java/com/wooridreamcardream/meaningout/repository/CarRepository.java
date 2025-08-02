package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>{
    Optional<Car> findByCategoryIdAndName(Long category_id, String name);
    List<Car> findByIdIn(List<Long> ids);
}
