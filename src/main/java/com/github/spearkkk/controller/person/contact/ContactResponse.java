package com.github.spearkkk.controller.person.contact;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactResponse {
  private final String phone;
  private final String email;
}
