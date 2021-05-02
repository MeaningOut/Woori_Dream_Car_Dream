package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
