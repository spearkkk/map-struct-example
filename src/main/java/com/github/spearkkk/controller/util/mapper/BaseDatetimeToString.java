package com.github.spearkkk.controller.util.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
@Mapping(target = "lastModifiedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
public @interface BaseDatetimeToString { }
