package com.github.spearkkk.controller.person;

import com.github.spearkkk.domain.person.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonResponseMapper {
    PersonResponse map(Person entity);
    List<PersonResponse> map(List<Person> entities);
}
