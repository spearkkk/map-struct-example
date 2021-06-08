package com.github.spearkkk.domain

import com.github.spearkkk.domain.person.Person
import com.github.spearkkk.domain.person.PersonRepository
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.transaction.Transactional
import java.time.LocalDateTime

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
  .stringLengthRange(1, 50)


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
