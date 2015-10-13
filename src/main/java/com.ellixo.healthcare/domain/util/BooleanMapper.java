package com.ellixo.healthcare.domain.util;

import org.springframework.stereotype.Component;

@Component
public class BooleanMapper {

    public String asString(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool ? "Oui" : "Non";
    }

    public Boolean asBoolean(String string) {
        if (string == null) {
            return null;
        }

        if (string.equalsIgnoreCase("Oui")) {
            return true;
        } else if (string.equalsIgnoreCase("Non")) {
            return false;
        } else {
            return null;
        }
    }

}
