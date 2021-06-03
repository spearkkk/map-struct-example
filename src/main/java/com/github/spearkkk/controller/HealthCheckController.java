package com.github.spearkkk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Value("${role}")
    private String role;

    @GetMapping(value = { "/", "/hello" })
    public String hello() {
        return "Hello, " + role;
    }
}
