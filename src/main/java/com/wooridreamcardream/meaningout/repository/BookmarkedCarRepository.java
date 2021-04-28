package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.BookmarkedCar;
import com.wooridreamcardream.meaningout.domain.Car;
import com.wooridreamcardream.meaningout.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedCarRepository extends JpaRepository<BookmarkedCar, Long> {
    Optional<BookmarkedCar> findByUserIdAndCarId(User user, Car car);

    List<BookmarkedCar> findByUserId(Long user_id);
}
