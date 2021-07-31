package com.github.spearkkk.controller.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyResponse {
  private final Long id;
  private final String name;
  private final String createdAt;
  private final String lastModifiedAt;
}
