package com.github.spearkkk.controller.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.spearkkk.controller.person.favorites.Favorites;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class FavoritesMapper {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  // Ref.: https://github.com/FasterXML/jackson-modules-java8/tree/master/parameter-names
  static {
    OBJECT_MAPPER.registerModule(new ParameterNamesModule());
  }

  public Favorites map(String entity) {
    if (entity == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(entity.getBytes(StandardCharsets.UTF_8), Favorites.class);
    } catch (IOException e) {
      log.warn("There is exception to read json value. `entity`: {}", entity, e);
      return null;
    }
  }
}
