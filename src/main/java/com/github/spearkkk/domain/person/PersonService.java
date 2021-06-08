package com.github.spearkkk.domain.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository repository;

    public List<Person> findAllPeople() {
        return repository.findAll();
    }

    public Person findPersonBy(Long id) {
        return repository.findById(id)
                         .orElseThrow(() ->  new IllegalArgumentException("Cannot find object. id: " + id));
    }
}
