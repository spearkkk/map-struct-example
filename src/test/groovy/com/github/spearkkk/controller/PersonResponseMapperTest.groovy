package com.github.spearkkk.controller

import com.github.spearkkk.controller.person.BirthdayMapperImpl
import com.github.spearkkk.controller.person.PersonResponseMapper
import com.github.spearkkk.controller.person.PersonResponseMapperImpl
import com.github.spearkkk.domain.person.Person
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.jeasy.random.randomizers.misc.ConstantRandomizer
import org.jeasy.random.randomizers.text.StringRandomizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ContextConfiguration(classes = [PersonResponseMapperImpl.class, BirthdayMapperImpl.class])
class PersonResponseMapperTest extends Specification {
  def easyRandom = new EasyRandom()

  @Autowired
  PersonResponseMapper mapper

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
    result.getContact().getPhone() == person.getContact().getPhoneNumber()
    result.getContact().getEmail() == person.getContact().getEmailAddress()
    !result.isBirthday()
    result.getCreatedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                              .format(person.getCreatedAt())
    result.getLastModifiedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                                   .format(person.getLastModifiedAt())
  }

  def "Mapper should map Person to PersonResponse if today is birthday."() {
    given:
    def easyRandomParameters = new EasyRandomParameters().dateRange(LocalDate.now(), LocalDate.now())
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    result.getName() == person.getName()
    result.getAddress().getPostCode() == person.getAddress().getPostCode()
    result.getAddress().getStreetAddress() == person.getAddress().getStreetAddress()
    result.getContact().getPhone() == person.getContact().getPhoneNumber()
    result.getContact().getEmail() == person.getContact().getEmailAddress()
    result.isBirthday()
  }

  def "Mapper should map Person to PersonResponse for `saidMommyAt` as LocalDateTime"() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .randomize(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"),
                   new ConstantRandomizer("2000-08-31T13:45:01"))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                     .format(result.getSaidMommyAt()) == person.getSaidMommyAt()
  }
}
