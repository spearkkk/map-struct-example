package com.github.spearkkk.controller.company;

import com.github.spearkkk.domain.company.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyResponseMapper {
  CompanyResponse map(Company entity);
  List<CompanyResponse> map(List<Company> entities);
}