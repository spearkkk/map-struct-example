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
               @Mapping(source = "entity.birthday", target = "isBirthday"),
               @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss"),
               @Mapping(target = "lastModifiedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")})
    PersonResponse map(Person entity);
    List<PersonResponse> map(List<Person> entities);
}
