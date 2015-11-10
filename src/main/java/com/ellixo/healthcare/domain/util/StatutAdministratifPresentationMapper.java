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

import com.ellixo.healthcare.domain.StatutAdministratifPresentation;
import com.ellixo.healthcare.domain.StatutBDM;
import org.springframework.stereotype.Component;

@Component
public class StatutAdministratifPresentationMapper {

    public String asString(StatutAdministratifPresentation statut) {
        if (statut == null) {
            return null;
        }
        return statut.getLibelle();
    }

    public StatutAdministratifPresentation asStatut(String string) {
        if (string == null) {
            return null;
        }

        return StatutAdministratifPresentation.fromCode(string);
    }

}
