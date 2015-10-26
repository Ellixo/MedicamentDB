package com.ellixo.healthcare.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.elasticsearch.common.base.Strings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateIIDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string = jsonParser.getText();
        if (Strings.isNullOrEmpty(string)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(string, dtf);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}