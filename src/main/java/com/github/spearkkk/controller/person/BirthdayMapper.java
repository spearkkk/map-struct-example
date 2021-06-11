package com.github.spearkkk.controller.person;

import com.github.spearkkk.domain.person.birthday.Birthday;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface BirthdayMapper {
  default boolean map(Birthday entity) {
    return LocalDate.now().getDayOfMonth() == entity.getDate().getDayOfMonth()
        && LocalDate.now().getMonthValue() == entity.getDate().getMonthValue();
  }
}
