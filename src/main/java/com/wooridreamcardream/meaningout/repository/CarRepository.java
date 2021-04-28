package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>{
    List<Car> findByMinPriceGreaterThanEqualAndMaxPriceLessThanEqual(BigDecimal minPrice, BigDecimal maxPrice);
    Optional<Car> findByCategoryIdAndName(Long category_id, String name);
}
