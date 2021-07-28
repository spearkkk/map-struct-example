package com.github.spearkkk.controller.person;

import com.github.spearkkk.domain.company.Company;
import com.github.spearkkk.domain.person.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonFromResponseMapper {
    String map(Person person, Company company) {
        return person.getName() + " from " + company.getName();
    }
}
