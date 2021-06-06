package com.github.spearkkk.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest
class PersonRepositoryTest extends Specification {
  @Autowired
  private PersonRepository repository

  def "Repository should find all."() {
    given:
    def person1st = Person.builder()
                       .name("NAME_1")
                       .build()
    def person2nd = Person.builder()
                          .name("NAME_2")
                          .build()
    def saved1st = repository.save(person1st)
    def saved2nd = repository.save(person2nd)

    when:
    def result = repository.findAll()

    then:
    !result.isEmpty()
    println(saved1st)
    println(saved2nd)
    result == [saved1st, saved2nd]
  }

  def "Repository should find data by ID."() {
    given:
    def person = Person.builder()
                          .name("NAME_1")
                          .build()
    def saved = repository.save(person)
    println(saved)

    when:
    def result = repository.findById(saved.getId())

    then:
    result.isPresent()
    result.get() == saved
  }
}
