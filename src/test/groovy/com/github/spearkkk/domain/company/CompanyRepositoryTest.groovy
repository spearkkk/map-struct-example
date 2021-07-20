package com.github.spearkkk.domain.company

import com.github.spearkkk.domain.BaseDatetime
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
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
class CompanyRepositoryTest extends Specification {
  def easyRandomParameters = new EasyRandomParameters()
      .excludeField(FieldPredicates.named("createdAt")
                        & FieldPredicates.inClass(BaseDatetime.class)
                        & FieldPredicates.ofType(LocalDateTime.class))
      .excludeField(FieldPredicates.named("lastModifiedAt")
                        & FieldPredicates.inClass(BaseDatetime.class)
                        & FieldPredicates.ofType(LocalDateTime.class))
      .excludeField(FieldPredicates.named("id")
                        & FieldPredicates.inClass(Company.class)
                        & FieldPredicates.ofType(Long.class))
      .randomize(FieldPredicates.named("name") & FieldPredicates.inClass(Company.class)
                     & FieldPredicates.ofType(String.class), new StringRandomizer(100))

  def easyRandom = new EasyRandom(easyRandomParameters)

  @Autowired
  private CompanyRepository repository

  def "Repository should find all."() {
    given:
    def company1st = easyRandom.nextObject(Company.class)
    def company2nd = easyRandom.nextObject(Company.class)

    def saved1st = repository.save(company1st)
    def saved2nd = repository.save(company2nd)

    when:
    def result = repository.findAll()

    then:
    !result.isEmpty()
    result == [saved1st, saved2nd]
  }

  def "Repository should find data by ID."() {
    given:
    def company = easyRandom.nextObject(Company.class)
    def saved = repository.save(company)

    when:
    def result = repository.findById(saved.getId())

    then:
    result.isPresent()
    result.get() == saved
  }
}
