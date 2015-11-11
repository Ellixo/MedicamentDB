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

@JsonPropertyOrder({"codeGroupe", "libelleGroupe", "codeCIS", "typeGenerique", "ordre"})
public class GroupeGeneriqueCSV {

    private String codeGroupe;
    private String libelleGroupe;
    private String codeCIS;
    private Integer typeGenerique;
    private Integer ordre;

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getLibelleGroupe() {
        return libelleGroupe;
    }

    public void setLibelleGroupe(String libelleGroupe) {
        this.libelleGroupe = libelleGroupe;
    }

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

    public Integer getTypeGenerique() {
        return typeGenerique;
    }

    public void setTypeGenerique(Integer typeGenerique) {
        this.typeGenerique = typeGenerique;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupeGeneriqueCSV that = (GroupeGeneriqueCSV) o;

        if (!codeGroupe.equals(that.codeGroupe)) return false;
        return codeCIS.equals(that.codeCIS);

    }

    @Override
    public int hashCode() {
        int result = codeGroupe.hashCode();
        result = 31 * result + codeCIS.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GroupeGeneriqueCSV{" +
                "codeGroupe='" + codeGroupe + '\'' +
                ", libelleGroupe='" + libelleGroupe + '\'' +
                ", codeCIS='" + codeCIS + '\'' +
                ", typeGenerique=" + typeGenerique +
                ", ordre=" + ordre +
                '}';
    }
}
