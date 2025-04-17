package com.cyperlo.web.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Primary
    @Bean(name = "jsonObjectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
