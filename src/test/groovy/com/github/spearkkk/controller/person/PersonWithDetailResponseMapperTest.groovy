package com.github.spearkkk.controller.person

import com.github.spearkkk.domain.company.Company
import com.github.spearkkk.domain.person.Person
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [PersonWithDetailResponseMapperImpl.class, BirthdayMapperImpl.class, CharacterMapperImpl.class, FavoritesMapperImpl.class])
class PersonWithDetailResponseMapperTest extends Specification {
  @Autowired
  PersonWithDetailResponseMapper mapper

  def "Mapper should map Person and Company to PersonWithDetailResponse."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)
    def company = easyRandom.nextObject(Company.class)

    when:
    def result = mapper.map(person, company.getName())

    then:
    result.getId() == person.getId()
    result.getName() == person.getName()
    result.getCompanyName() == company.getName()
  }
}
