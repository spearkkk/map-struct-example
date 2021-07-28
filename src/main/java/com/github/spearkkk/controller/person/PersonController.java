package com.github.spearkkk.controller.person;

import com.github.spearkkk.domain.company.Company;
import com.github.spearkkk.domain.company.CompanyService;
import com.github.spearkkk.domain.person.Person;
import com.github.spearkkk.domain.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonController {
  private final PersonService personService;
  private final PersonResponseMapper personResponseMapper;
  private final CompanyService companyService;
  private final PersonWithDetailResponseMapper personWithDetailResponseMapper;
  private final PersonFromResponseMapper personFromResponseMapper;

  @GetMapping(value = "/people")
  public List<PersonResponse> getPeople() {
    return personResponseMapper.map(personService.findAllPeople());
  }

  @GetMapping(value = "/people/{id}")
  public PersonResponse getPerson(@PathVariable Long id) {
    return personResponseMapper.map(personService.findPersonBy(id));
  }

  @GetMapping(value = "/people/{id}/detail")
  public PersonWithDetailResponse getPersonWithDetail(@PathVariable Long id) {
    Person person = personService.findPersonBy(id);
    Company company = companyService.findCompanyBy(person.getCompanyId());
    return personWithDetailResponseMapper.map(person, company);
  }

  @GetMapping(value = "/people/{id}/from")
  public String getPersonFrom(@PathVariable Long id) {
    Person person = personService.findPersonBy(id);
    Company company = companyService.findCompanyBy(person.getCompanyId());

    return personFromResponseMapper.map(person, company);
  }
}
