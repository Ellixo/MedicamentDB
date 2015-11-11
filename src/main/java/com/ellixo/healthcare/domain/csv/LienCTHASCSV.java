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

package com.ellixo.healthcare.domain.csv;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"codeDossierHAS", "urlHAS"})
public class LienCTHASCSV {

    private String codeDossierHAS;
    private String urlHAS;

    public String getCodeDossierHAS() {
        return codeDossierHAS;
    }

    public void setCodeDossierHAS(String codeDossierHAS) {
        this.codeDossierHAS = codeDossierHAS;
    }

    public String getUrlHAS() {
        return urlHAS;
    }

    public void setUrlHAS(String urlHAS) {
        this.urlHAS = urlHAS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LienCTHASCSV lienCTHAS = (LienCTHASCSV) o;

        if (!codeDossierHAS.equals(lienCTHAS.codeDossierHAS)) return false;
        return urlHAS.equals(lienCTHAS.urlHAS);

    }

    @Override
    public int hashCode() {
        int result = codeDossierHAS.hashCode();
        result = 31 * result + urlHAS.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LienCTHASCSV{" +
                "codeDossierHAS='" + codeDossierHAS + '\'' +
                ", urlHAS='" + urlHAS + '\'' +
                '}';
    }
}
