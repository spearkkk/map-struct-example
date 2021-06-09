package com.github.spearkkk.controller

import com.github.spearkkk.controller.person.PersonResponseMapper
import com.github.spearkkk.domain.person.Person
import org.jeasy.random.EasyRandom
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class PersonResponseMapperTest extends Specification {
  def easyRandom = new EasyRandom()

  def mapper = Mappers.getMapper(PersonResponseMapper.class)

  def "Mapper should map Person to PersonResponse."() {
    given:
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    result.getName() == person.getName()
    result.getAddress().getPostCode() == person.getAddress().getPostCode()
    result.getAddress().getStreetAddress() == person.getAddress().getStreetAddress()
    result.getCreatedAt() == person.getCreatedAt()
    result.getLastModifiedAt() == person.getLastModifiedAt()
  }
}
