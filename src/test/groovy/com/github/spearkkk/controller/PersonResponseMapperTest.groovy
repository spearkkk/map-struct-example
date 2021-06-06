package com.github.spearkkk.controller

import com.github.spearkkk.domain.PersonDTO
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class PersonResponseMapperTest extends Specification {
  def mapper = Mappers.getMapper(PersonResponseMapper.class)

  def "Mapper should map PersonDTO to PersonResponse."() {
    given:
    def dto = PersonDTO.builder()
                       .id(1L)
                       .name("NAME")
                       .build()

    when:
    def result = mapper.map(dto)

    then:
    result.getId() == 1L
    result.getName() == "NAME"
  }
}
