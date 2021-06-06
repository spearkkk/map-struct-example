package com.github.spearkkk.domain;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {
    PersonDTO map(Person entity);
    List<PersonDTO> map(List<Person> entities);
}
