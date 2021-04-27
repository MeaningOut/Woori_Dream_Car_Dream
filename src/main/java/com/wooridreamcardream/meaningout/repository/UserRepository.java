package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
