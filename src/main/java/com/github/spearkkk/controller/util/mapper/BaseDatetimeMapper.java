package com.github.spearkkk.controller.util.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface BaseDatetimeMapper {
  default String map(LocalDateTime dateTime) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(dateTime);
  }
}
