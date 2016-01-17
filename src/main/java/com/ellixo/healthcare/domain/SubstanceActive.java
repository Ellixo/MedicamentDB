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

import java.util.ArrayList;
import java.util.List;

public class SubstanceActive extends Substance {

    private List<FractionTherapeutique> fractionsTherapeutiques = new ArrayList<>();

    public List<FractionTherapeutique> getFractionsTherapeutiques() {
        return fractionsTherapeutiques;
    }

    public void setFractionsTherapeutiques(List<FractionTherapeutique> fractionsTherapeutiques) {
        this.fractionsTherapeutiques = fractionsTherapeutiques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubstanceActive that = (SubstanceActive) o;

        return getCodeSubstance().equals(that.getCodeSubstance());

    }

    @Override
    public int hashCode() {
        return getCodeSubstance().hashCode();
    }

    @Override
    public String toString() {
        return "SubstanceActive{" +
                "codeSubstance='" + getCodeSubstance() + '\'' +
                ", denominationSubstance='" + getDenominationSubstance() + '\'' +
                ", dosageSubstance='" + getDosageSubstance() + '\'' +
                ", numero=" + getNumero() +
                ", fractionsTherapeutiques=" + fractionsTherapeutiques +
                '}';
    }
}
