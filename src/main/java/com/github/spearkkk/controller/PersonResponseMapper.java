package com.github.spearkkk.controller;

import com.github.spearkkk.domain.PersonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonResponseMapper {
    PersonResponse map(PersonDTO entity);
    List<PersonResponse> map(List<PersonDTO> entities);
}
