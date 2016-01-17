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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

public abstract class Substance {

    private String codeSubstance;
    private String denominationSubstance;
    private String dosageSubstance;
    @Field(
            index = FieldIndex.no,
            store = true
    )
    private List<Interaction> interactions = new ArrayList<>();
    @JsonIgnore
    private Integer numero;

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public String getCodeSubstance() {
        return codeSubstance;
    }

    public void setCodeSubstance(String codeSubstance) {
        this.codeSubstance = codeSubstance;
    }

    public String getDenominationSubstance() {
        return denominationSubstance;
    }

    public void setDenominationSubstance(String denominationSubstance) {
        this.denominationSubstance = denominationSubstance;
    }

    public String getDosageSubstance() {
        return dosageSubstance;
    }

    public void setDosageSubstance(String dosageSubstance) {
        this.dosageSubstance = dosageSubstance;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Substance that = (Substance) o;

        return codeSubstance.equals(that.codeSubstance);

    }

    @Override
    public int hashCode() {
        return codeSubstance.hashCode();
    }

    @Override
    public String toString() {
        return "SubstanceActive{" +
                "codeSubstance='" + codeSubstance + '\'' +
                ", denominationSubstance='" + denominationSubstance + '\'' +
                ", dosageSubstance='" + dosageSubstance + '\'' +
                ", numero=" + numero +
                '}';
    }

    public static class Interaction {
        private String id;
        private String idFamille1;
        private String famille1;
        private String idFamille2;
        private String famille2;

        public Interaction() {
        }

        public Interaction(String id, String idFamille1, String famille1, String idFamille2, String famille2) {
            this.id = id;
            this.idFamille1 = idFamille1;
            this.famille1 = famille1;
            this.idFamille2 = idFamille2;
            this.famille2 = famille2;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdFamille1() {
            return idFamille1;
        }

        public void setIdFamille1(String idFamille1) {
            this.idFamille1 = idFamille1;
        }

        public String getFamille1() {
            return famille1;
        }

        public void setFamille1(String famille1) {
            this.famille1 = famille1;
        }

        public String getIdFamille2() {
            return idFamille2;
        }

        public void setIdFamille2(String idFamille2) {
            this.idFamille2 = idFamille2;
        }

        public String getFamille2() {
            return famille2;
        }

        public void setFamille2(String famille2) {
            this.famille2 = famille2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Interaction that = (Interaction) o;

            return id.equals(that.id);

        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
