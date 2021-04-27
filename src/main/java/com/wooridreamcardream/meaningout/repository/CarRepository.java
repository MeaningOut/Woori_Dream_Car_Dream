package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long>{
    List<Car> findByMinPriceGreaterThanEqualAndMaxPriceLessThanEqual(BigDecimal minPrice, BigDecimal maxPrice);

    List<Car> findByCC(String cc);
}
