package com.github.spearkkk.domain.person;

import com.github.spearkkk.domain.BaseDatetime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
