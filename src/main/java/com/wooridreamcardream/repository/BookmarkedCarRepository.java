package com.wooridreamcardream.repository;

import com.wooridreamcardream.domain.BookmarkedCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkedCarRepository extends JpaRepository<BookmarkedCar, Long> {
}
