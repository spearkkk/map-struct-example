package com.github.spearkkk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckControllerTest extends Specification {
  @Autowired
  TestRestTemplate template

  def "HealthCheckController should return welcome message."() {
    expect:
    template.getForObject("/hello", String.class) == "Hello, map_struct_example"
  }
}
