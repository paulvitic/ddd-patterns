package net.vitic.ddd.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class MapJsonStringConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(dbData, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
}
