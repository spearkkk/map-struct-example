package com.github.spearkkk.controller

import com.github.spearkkk.controller.person.PersonResponse
import com.github.spearkkk.domain.person.Person
import com.github.spearkkk.domain.person.PersonService
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest extends Specification {
  @Autowired
  TestRestTemplate restTemplate

  @SpringSpy
  PersonService personService

  def "Controller should return people."() {
    given:
    def entity1st = Stub(Person) {
      getId() >> 1L
      getName() >> "NAME_1"
    }
    def entity2nd = Stub(Person) {
      getId() >> 2L
      getName() >> "NAME_2"
    }

    def people = [entity1st, entity2nd]

    when:
    def result = restTemplate.getForObject("/people", List<PersonResponse>.class)

    then:
    1 * personService.findAllPeople() >> people
    !result.isEmpty()
  }

  def "Controller should return person."() {
    given:
    def entity1st = Stub(Person) {
      getId() >> 1L
      getName() >> "NAME_1"
    }

    when:
    def result = restTemplate.getForObject("/people/1", PersonResponse.class)

    then:
    1 * personService.findPersonBy(1L) >> entity1st
    result.getId() == 1L
    result.getName() == "NAME_1"
  }
}
