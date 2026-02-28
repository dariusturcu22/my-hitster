package org.dariusturcu.backend.model.ai;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class FlexibleYearDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getText().trim().replaceAll("[^0-9]", "");
        return value.isEmpty() ? 0 : Integer.parseInt(value.substring(0, Math.min(4, value.length())));
    }
}