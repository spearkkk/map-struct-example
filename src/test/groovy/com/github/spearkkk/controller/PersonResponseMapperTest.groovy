package com.github.spearkkk.controller

import com.github.spearkkk.controller.person.BirthdayMapperImpl
import com.github.spearkkk.controller.person.CharacterMapperImpl
import com.github.spearkkk.controller.person.FavoritesMapperImpl
import com.github.spearkkk.controller.person.PersonResponseMapper
import com.github.spearkkk.controller.person.PersonResponseMapperImpl
import com.github.spearkkk.domain.person.Person
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.jeasy.random.randomizers.misc.ConstantRandomizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ContextConfiguration(classes = [PersonResponseMapperImpl.class, BirthdayMapperImpl.class, CharacterMapperImpl.class, FavoritesMapperImpl.class])
class PersonResponseMapperTest extends Specification {
  @Autowired
  PersonResponseMapper mapper

  def "Mapper should map Person to PersonResponse."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
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
    !result.isBirthday()
    result.getCreatedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                              .format(person.getCreatedAt())
    result.getLastModifiedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                                   .format(person.getLastModifiedAt())
  }

  def "Mapper should map Person to PersonResponse if today is birthday."() {
    given:
    def easyRandomParameters = new EasyRandomParameters().dateRange(LocalDate.now(), LocalDate.now())
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
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

  def "Mapper should map Person to PersonResponse for `saidMommyAt` as LocalDateTime."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
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

  def "Mapper should map Person to PersonResponse for `character` as String."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
        .randomize(FieldPredicates.ofType(Map.class) & FieldPredicates.named("character"),
                   new ConstantRandomizer<Map<String, String>>(["key1": "value1", "key2": "value2"]))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    result.getCharacter() == """{"key1":"value1","key2":"value2"}"""
  }

  def "Mapper should map Person to PersonResponse for `character` as empty json if `character` is null."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"))
        .randomize(FieldPredicates.ofType(Map.class) & FieldPredicates.named("character"),
                   new ConstantRandomizer<Map<String, String>>(null))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    result.getCharacter() == "{}"
  }

  def "Mapper should map Person to PersonResponse for `favorites` as Object."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
        .excludeField(FieldPredicates.ofType(String.class) & FieldPredicates.named("saidMommyAt"))
        .excludeField(FieldPredicates.ofType(Map.class) & FieldPredicates.named("character"))
        .randomize(FieldPredicates.ofType(String.class) & FieldPredicates.named("favorites"),
                   new ConstantRandomizer<String>("""{"food":"pizza","color":"black"}"""))
    def easyRandom = new EasyRandom(easyRandomParameters)
    def person = easyRandom.nextObject(Person.class)

    when:
    def result = mapper.map(person)

    then:
    result.getId() == person.getId()
    result.getFavorites().getFood() == "pizza"
    result.getFavorites().getColor() == "black"
  }
}
