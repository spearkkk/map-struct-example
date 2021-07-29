package com.github.spearkkk.controller.company;

import com.github.spearkkk.domain.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CompanyController {
  private final CompanyService companyService;
  private final CompanyResponseMapper companyResponseMapper;

  @GetMapping(value = "/companies")
  public List<CompanyResponse> getPeople() {
    return companyResponseMapper.map(companyService.findAllCompanies());
  }
}
