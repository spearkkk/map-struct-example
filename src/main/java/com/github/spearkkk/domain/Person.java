package com.github.spearkkk.domain;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {
    Long id;
    String name;
}
