package com.github.spearkkk.controller.person

import com.github.spearkkk.domain.company.Company
import com.github.spearkkk.domain.person.Person
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate

@ContextConfiguration(classes = PersonFromResponseMapperImpl.class)
class PersonFromResponseMapperTest extends Specification {
  @Autowired
  PersonFromResponseMapper mapper

  def "Mapper should map Person and Company to PersonFromResponse."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .dateRange(LocalDate.now(), LocalDate.now())
        .excludeField(FieldPredicates.ofType(String.class)
                          & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class)
                          & FieldPredicates.named("favorites"))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    def company = easyRandom.nextObject(Company.class)

    when:
    def result = mapper.map(person, company)

    then:
    result == "${person.getName()} from ${company.getName()}"
  }
}
