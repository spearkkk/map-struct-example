package com.github.spearkkk.controller.person

import com.github.spearkkk.domain.company.Company
import com.github.spearkkk.domain.company.CompanyService
import com.github.spearkkk.domain.person.Person
import com.github.spearkkk.domain.person.PersonService
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest extends Specification {
  def easyRandomParameters = new EasyRandomParameters()
      .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
  def easyRandom = new EasyRandom(easyRandomParameters)

  @Autowired
  TestRestTemplate restTemplate

  @SpringSpy
  PersonService personService
  @SpringSpy
  CompanyService companyService

  def "Controller should return people."() {
    given:
    def entity1st = easyRandom.nextObject(Person.class)
    def entity2nd = easyRandom.nextObject(Person.class)

    def people = [entity1st, entity2nd]

    when:
    def result = restTemplate.getForObject("/people", List<PersonResponse>.class)

    then:
    1 * personService.findAllPeople() >> people
    !result.isEmpty()
  }

  def "Controller should return person."() {
    given:
    def entity = easyRandom.nextObject(Person.class)

    when:
    def result = restTemplate.getForObject("/people/1", PersonResponse.class)

    then:
    1 * personService.findPersonBy(1L) >> entity
    result.getId() == entity.getId()
    result.getName() == entity.getName()
    result.getCreatedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(entity.getCreatedAt())
    result.getLastModifiedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(entity.getLastModifiedAt())
  }

  def "Controller should return person with detail."() {
    given:
    def person = easyRandom.nextObject(Person.class)
    def company = easyRandom.nextObject(Company.class)

    when:
    def result = restTemplate.getForObject("/people/1/detail", PersonWithDetailResponse.class)

    then:
    1 * personService.findPersonBy(1L) >> person
    1 * companyService.findCompanyBy(person.getCompanyId()) >> company
    result.getId() == person.getId()
    result.getName() == person.getName()
    result.getCompanyName() == company.getName()
    result.getCreatedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(person.getCreatedAt())
    result.getLastModifiedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(person.getLastModifiedAt())
  }

  def "Controller should return person from."() {
    given:
    def person = easyRandom.nextObject(Person.class)
    def company = easyRandom.nextObject(Company.class)

    when:
    def result = restTemplate.getForObject("/people/1/from", String.class)

    then:
    1 * personService.findPersonBy(1L) >> person
    1 * companyService.findCompanyBy(person.getCompanyId()) >> company

    result == "${person.getName()} from ${company.getName()}"
  }
}
