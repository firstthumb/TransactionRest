package com.ekocaman.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ComponentScan("com.ekocaman.demo")
public class AppConfig {

    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new Jdk8Module());
    }
}
