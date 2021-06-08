package com.github.spearkkk.controller

import com.github.spearkkk.controller.person.PersonResponseMapper
import com.github.spearkkk.domain.person.Person
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class PersonResponseMapperTest extends Specification {
  def mapper = Mappers.getMapper(PersonResponseMapper.class)

  def "Mapper should map Person to PersonResponse."() {
    given:
    def person = Person.builder()
                       .id(1L)
                       .name("NAME")
                       .build()

    when:
    def result = mapper.map(person)

    then:
    result.getId() == 1L
    result.getName() == "NAME"
  }
}
