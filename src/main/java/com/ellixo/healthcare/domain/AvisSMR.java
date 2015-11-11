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

import com.ellixo.healthcare.domain.util.DateESDeserializer;
import com.ellixo.healthcare.domain.util.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

public class AvisSMR {

    private String codeDossierHAS;
    private String motifEvaluation;
    @Field(
            type = FieldType.Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd"
    )
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateESDeserializer.class)
    private Date dateAvisCommissionTransparence;
    private String valeurSMR;
    private String libelleSMR;

    private String urlHAS;

    public String getCodeDossierHAS() {
        return codeDossierHAS;
    }

    public void setCodeDossierHAS(String codeDossierHAS) {
        this.codeDossierHAS = codeDossierHAS;
    }

    public String getMotifEvaluation() {
        return motifEvaluation;
    }

    public void setMotifEvaluation(String motifEvaluation) {
        this.motifEvaluation = motifEvaluation;
    }

    public Date getDateAvisCommissionTransparence() {
        return dateAvisCommissionTransparence;
    }

    public void setDateAvisCommissionTransparence(Date dateAvisCommissionTransparence) {
        this.dateAvisCommissionTransparence = dateAvisCommissionTransparence;
    }

    public String getValeurSMR() {
        return valeurSMR;
    }

    public void setValeurSMR(String valeurSMR) {
        this.valeurSMR = valeurSMR;
    }

    public String getLibelleSMR() {
        return libelleSMR;
    }

    public void setLibelleSMR(String libelleSMR) {
        this.libelleSMR = libelleSMR;
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

        AvisSMR avisSMR = (AvisSMR) o;

        return codeDossierHAS.equals(avisSMR.codeDossierHAS);

    }

    @Override
    public int hashCode() {
        return codeDossierHAS.hashCode();
    }

    @Override
    public String toString() {
        return "AvisSMR{" +
                "codeDossierHAS='" + codeDossierHAS + '\'' +
                ", motifEvaluation='" + motifEvaluation + '\'' +
                ", dateAvisCommissionTransparence=" + dateAvisCommissionTransparence +
                ", valeurSMR='" + valeurSMR + '\'' +
                ", libelleSMR='" + libelleSMR + '\'' +
                '}';
    }
}
