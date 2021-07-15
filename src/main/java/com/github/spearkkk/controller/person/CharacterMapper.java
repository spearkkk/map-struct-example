package com.github.spearkkk.controller.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class CharacterMapper {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public String map(Map<String, String> entity) throws JsonProcessingException {
    if (entity == null) {
      return "{}";
    }
    return OBJECT_MAPPER.writeValueAsString(entity);
  }
}
