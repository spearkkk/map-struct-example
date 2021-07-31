package com.github.spearkkk.domain.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public List<Company> findAllCompanies() {
    return companyRepository.findAll();
  }

  public Company findCompanyBy(Long id) {
    return companyRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Cannot find object. id: " + id));
  }
}
