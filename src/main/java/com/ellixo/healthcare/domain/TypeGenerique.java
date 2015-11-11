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

package com.ellixo.healthcare.domain;

public enum TypeGenerique {
    PRINCEPS(0), GENERIQUE(1), GENERIQUE_COMPLEMENT_POSOLOGIQUE(2), GENERIQUE_SUBSTITUABLE(4);

    private int code;

    TypeGenerique(int code) {
        this.code = code;
    }

    public static TypeGenerique fromCode(int code) {
        for (TypeGenerique type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
