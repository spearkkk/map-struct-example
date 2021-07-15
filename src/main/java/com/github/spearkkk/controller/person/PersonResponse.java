package com.github.spearkkk.controller.person;

import com.github.spearkkk.controller.person.address.AddressResponse;
import com.github.spearkkk.controller.person.contact.ContactResponse;
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
  private final ContactResponse contact;
  private final String createdAt;
  private final String lastModifiedAt;
  private final boolean isBirthday;
  private final LocalDateTime saidMommyAt;
  private final String character;
}
