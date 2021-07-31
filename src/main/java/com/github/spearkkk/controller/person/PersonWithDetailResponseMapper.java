package com.github.spearkkk.controller.person;

import com.github.spearkkk.controller.util.mapper.BaseDatetimeToString;
import com.github.spearkkk.domain.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { BirthdayMapper.class, CharacterMapper.class, FavoritesMapper.class })
public interface PersonWithDetailResponseMapper {
    @BaseDatetimeToString
    @Mappings({@Mapping(source = "person.id", target = "id"), @Mapping(source = "person.name", target = "name"),
               @Mapping(source = "person.contact.phoneNumber", target = "contact.phone"),
               @Mapping(source = "person.contact.emailAddress", target = "contact.email"),
               @Mapping(source = "person.birthday", target = "isBirthday"),
               @Mapping(target = "saidMommyAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss"),
               @Mapping(source = "companyName", target = "companyName")})
    PersonWithDetailResponse map(Person person, String companyName);
}
