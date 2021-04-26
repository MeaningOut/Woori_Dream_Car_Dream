package com.wooridreamcardream.repository;

import com.wooridreamcardream.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
