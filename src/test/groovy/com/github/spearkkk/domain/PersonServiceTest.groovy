package com.github.spearkkk.domain

import spock.lang.Specification

class PersonServiceTest extends Specification {
  def repository = Mock(PersonRepository)

  def service = new PersonService(repository)

  def "Service should find all people."() {
    given:
    def people = [Stub(Person) {
      getId() >> 1L
      getName() >> "NAME_1"
    }, Stub(Person) {
      getId() >> 2L
      getName() >> "NAME_2"
    }]

    when:
    def result = service.findAllPeople()

    then:
    1 * repository.findAll() >> people
    !result.isEmpty()
  }

  def "Service should find person by ID."() {
    given:
    def person = Stub(Person) {
      getId() >> 1L
      getName() >> "NAME_1"
    }

    when:
    def result = service.findPersonBy(1L)

    then:
    1 * repository.findById(1L) >> Optional.of(person)
    result.getId() == 1L
    result.getName() == "NAME_1"
  }
}
