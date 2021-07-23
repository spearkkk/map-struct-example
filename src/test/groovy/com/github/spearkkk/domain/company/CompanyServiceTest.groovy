package com.github.spearkkk.domain.company

import spock.lang.Specification

class CompanyServiceTest extends Specification {
  def repository = Mock(CompanyRepository)

  def service = new CompanyService(repository)

  def "Service should find company by ID."() {
    given:
    def company = Stub(Company) {
      getId() >> 1L
      getName() >> "NAME_1"
    }

    when:
    def result = service.findCompanyBy(1L)

    then:
    1 * repository.findById(1L) >> Optional.of(company)
    result.getId() == 1L
    result.getName() == "NAME_1"
  }
}
