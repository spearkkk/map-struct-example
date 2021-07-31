package com.github.spearkkk.controller.company

import com.github.spearkkk.domain.company.Company
import com.github.spearkkk.domain.company.CompanyService
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerTest extends Specification {
  def easyRandomParameters = new EasyRandomParameters()
  def easyRandom = new EasyRandom(easyRandomParameters)

  @Autowired
  TestRestTemplate restTemplate

  @SpringSpy
  CompanyService companyService

  def "Controller should return companies."() {
    given:
    def entity1st = easyRandom.nextObject(Company.class)
    def entity2nd = easyRandom.nextObject(Company.class)

    def companies = [entity1st, entity2nd]

    when:
    def result = restTemplate.getForObject("/companies", List<CompanyResponse>.class)

    then:
    1 * companyService.findAllCompanies() >> companies
    println result
    !result.isEmpty()
    result.size() == 2
  }
}
