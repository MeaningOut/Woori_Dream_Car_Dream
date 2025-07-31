package com.wooridreamcardream.meaningout.service;

import com.wooridreamcardream.meaningout.domain.Company;
import com.wooridreamcardream.meaningout.dto.CompanyResponseDto;
import com.wooridreamcardream.meaningout.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponseDto findByName(String name) {
        Company company = companyRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. company = " + name));
        return new CompanyResponseDto(company);
    }

    public CompanyResponseDto findById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회사가 없습니다. company = " + id));
        return new CompanyResponseDto(company);
    }
}
