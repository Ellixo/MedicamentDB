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
