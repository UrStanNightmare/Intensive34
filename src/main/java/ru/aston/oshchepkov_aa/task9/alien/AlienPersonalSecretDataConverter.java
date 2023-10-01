package ru.aston.oshchepkov_aa.task9.alien;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class AlienPersonalSecretDataConverter implements AttributeConverter<AlienPersonalSecretData, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AlienPersonalSecretData alienPersonalSecretData) {
        try {
            return mapper.writer().writeValueAsString(alienPersonalSecretData);
        } catch (JsonProcessingException e) {
            log.error("{}", e.getMessage(), e);
            return "{}";
        }
    }
    @Override
    public AlienPersonalSecretData convertToEntityAttribute(String string) {
        try {
            return mapper.readValue(string, AlienPersonalSecretData.class);
        } catch (JsonProcessingException e) {
            log.error("{}", e.getMessage(), e);
            return null;
        }
    }
}
