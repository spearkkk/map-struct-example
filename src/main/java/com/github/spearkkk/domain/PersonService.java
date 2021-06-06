package com.github.spearkkk.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository repository;
    private final PersonDTOMapper mapper;

    public List<PersonDTO> findAllPeople() {
        return mapper.map(repository.findAll());
    }

    public PersonDTO findPersonBy(Long id) {
        return repository.findById(id)
                         .map(mapper::map)
                         .orElseThrow(() ->  new IllegalArgumentException("Cannot find object. id: " + id));
    }
}
