package com.github.spearkkk.controller;

import com.github.spearkkk.domain.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonController {
  private final PersonService personService;
  private final PersonResponseMapper responseMapper;

  @GetMapping(value = "/people")
  public List<PersonResponse> getPeople() {
    return responseMapper.map(personService.findAllPeople());
  }

  @GetMapping(value = "/people/{id}")
  public PersonResponse getPerson(@PathVariable Long id) {
    return responseMapper.map(personService.findPersonBy(id));
  }
}
