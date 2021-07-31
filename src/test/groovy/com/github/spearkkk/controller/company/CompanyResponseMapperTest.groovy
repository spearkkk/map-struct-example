package com.github.spearkkk.controller.company


import com.github.spearkkk.domain.company.Company
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.format.DateTimeFormatter

@ContextConfiguration(classes = CompanyResponseMapperImpl.class)
class CompanyResponseMapperTest extends Specification {
  @Autowired
  CompanyResponseMapper mapper

  def "Mapper should map Company to CompanyResponse."() {
    given:
    def easyRandomParameters = new EasyRandomParameters()
    def easyRandom = new EasyRandom(easyRandomParameters)
    def company = easyRandom.nextObject(Company.class)

    when:
    def result = mapper.map(company)

    then:
    result.getId() == company.getId()
    result.getName() == company.getName()
    result.getCreatedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                              .format(company.getCreatedAt())
    result.getLastModifiedAt() == DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                                   .format(company.getLastModifiedAt())
  }
}
