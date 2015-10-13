package com.ellixo.healthcare.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.elasticsearch.common.base.Strings;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

public class FloatDeserializer extends JsonDeserializer<Float> {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.###");

    @Override
    public Float deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string = jsonParser.getText();
        if (Strings.isNullOrEmpty(string)) {
            return null;
        }

        int index = string.lastIndexOf(',');
        if (index != -1) {
            string = string.substring(0, index) + "." + string.substring(index + 1);
        }

        try {
            return DECIMAL_FORMAT.parse(string).floatValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}