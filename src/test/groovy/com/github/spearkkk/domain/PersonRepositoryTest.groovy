package com.github.spearkkk.domain

import com.github.spearkkk.domain.person.Person
import com.github.spearkkk.domain.person.PersonRepository
import com.github.spearkkk.domain.person.address.Address
import com.github.spearkkk.domain.person.contact.Contact
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.jeasy.random.randomizers.misc.ConstantRandomizer
import org.jeasy.random.randomizers.text.StringRandomizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import spock.lang.Specification

import javax.transaction.Transactional
import java.time.LocalDateTime

@EnableJpaAuditing
@Transactional
@SpringBootTest
class PersonRepositoryTest extends Specification {
  def easyRandomParameters = new EasyRandomParameters()
      .excludeField(FieldPredicates.named("createdAt")
                        & FieldPredicates.inClass(BaseDatetime.class)
                        & FieldPredicates.ofType(LocalDateTime.class))
      .excludeField(FieldPredicates.named("lastModifiedAt")
                        & FieldPredicates.inClass(BaseDatetime.class)
                        & FieldPredicates.ofType(LocalDateTime.class))
      .excludeField(FieldPredicates.named("id")
                        & FieldPredicates.inClass(Person.class)
                        & FieldPredicates.ofType(Long.class))
      .randomize(FieldPredicates.named("name") & FieldPredicates.inClass(Person.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(100))
      .randomize(FieldPredicates.named("postCode") & FieldPredicates.inClass(Address.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(10))
      .randomize(FieldPredicates.named("streetAddress")
                     & FieldPredicates.inClass(Address.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(255))
      .randomize(FieldPredicates.named("phoneNumber") & FieldPredicates.inClass(Contact.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(20))
      .randomize(FieldPredicates.named("emailAddress")
                     & FieldPredicates.inClass(Contact.class) & FieldPredicates.ofType(String.class),
                 new StringRandomizer(255))
      .randomize(FieldPredicates.named("saidMommyAt") & FieldPredicates.inClass(Person.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(20))
      .randomize(FieldPredicates.named("character") & FieldPredicates.inClass(Person.class)
                     & FieldPredicates.ofType(Map.class),
                 new ConstantRandomizer<Map<String, String>>(["key":"value"]))
      .randomize(FieldPredicates.named("favorites") & FieldPredicates.inClass(Person.class)
                     & FieldPredicates.ofType(String.class),
                 new ConstantRandomizer<String>("""{"food":"pizza","bald":false,"":""}"""))

  def easyRandom = new EasyRandom(easyRandomParameters)

  @Autowired
  private PersonRepository repository

  def "Repository should find all."() {
    given:
    def person1st = easyRandom.nextObject(Person.class)
    def person2nd = easyRandom.nextObject(Person.class)

    def saved1st = repository.save(person1st)
    def saved2nd = repository.save(person2nd)

    when:
    def result = repository.findAll()

    then:
    !result.isEmpty()
    result == [saved1st, saved2nd]
  }

  def "Repository should find data by ID."() {
    given:
    def person = easyRandom.nextObject(Person.class)
    def saved = repository.save(person)

    when:
    def result = repository.findById(saved.getId())

    then:
    result.isPresent()
    result.get() == saved
  }
}
