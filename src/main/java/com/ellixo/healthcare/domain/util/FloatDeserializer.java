/*
 * Open Medicaments
 * Copyright (C) 2015  Ellixo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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