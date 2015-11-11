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

import com.ellixo.healthcare.domain.util.DateHASDeserializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonPropertyOrder({"codeCIS", "codeDossierHAS", "motifEvaluation", "dateAvisCommissionTransparence", "valeurSMR", "libelleSMR"})
public class AvisSMRCSV {

    private String codeCIS;
    private String codeDossierHAS;
    private String motifEvaluation;
    @JsonDeserialize(using = DateHASDeserializer.class)
    private Date dateAvisCommissionTransparence;
    private String valeurSMR;
    private String libelleSMR;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvisSMRCSV avisSMR = (AvisSMRCSV) o;

        return codeCIS.equals(avisSMR.codeCIS);

    }

    @Override
    public int hashCode() {
        return codeCIS.hashCode();
    }

    @Override
    public String toString() {
        return "AvisSMRCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", codeDossierHAS='" + codeDossierHAS + '\'' +
                ", motifEvaluation='" + motifEvaluation + '\'' +
                ", dateAvisCommissionTransparence=" + dateAvisCommissionTransparence +
                ", valeurSMR='" + valeurSMR + '\'' +
                ", libelleSMR='" + libelleSMR + '\'' +
                '}';
    }
}
