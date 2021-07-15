package com.github.spearkkk.domain.person;

import com.github.spearkkk.domain.BaseDatetime;
import com.github.spearkkk.domain.person.address.Address;
import com.github.spearkkk.domain.person.birthday.Birthday;
import com.github.spearkkk.domain.person.contact.Contact;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

@TypeDefs(@TypeDef(name = "json", typeClass = JsonStringType.class))
@ToString(callSuper = true)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "person")
@Entity
public class Person extends BaseDatetime {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;
  @Column(length = 100)
  private String name;
  @Embedded
  private Address address;
  @Embedded
  private Contact contact;
  @Embedded
  private Birthday birthday;
  @Column(length = 20)
  private String saidMommyAt;
  @Type(type = "json")
  @Column(columnDefinition = "json")
  private Map<String, String> character;
}