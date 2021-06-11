package com.github.spearkkk.controller.person;

import com.github.spearkkk.domain.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = BirthdayMapper.class)
public interface PersonResponseMapper {
    @Mappings({@Mapping(source = "entity.contact.phoneNumber", target = "contact.phone"),
               @Mapping(source = "entity.contact.emailAddress", target = "contact.email"),
               @Mapping(source = "entity.birthday", target = "isBirthday")})
    PersonResponse map(Person entity);
    List<PersonResponse> map(List<Person> entities);
}
