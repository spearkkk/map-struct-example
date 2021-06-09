package com.github.spearkkk.controller.person;

import com.github.spearkkk.controller.person.address.AddressResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonResponse {
  private final Long id;
  private final String name;
  private final AddressResponse address;
  private final LocalDateTime createdAt;
  private final LocalDateTime lastModifiedAt;
}
