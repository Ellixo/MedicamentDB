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

import com.ellixo.healthcare.domain.util.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"codeCIS", "denomination", "formePharmaceutique", "voiesAdministration", "statutAdministratifAMM", "typeProcedureAMM", "etatCommercialisation", "dateAMM", "statutBDM", "numeroAutorisationEuropeenne", "titulaires", "surveillanceRenforcee"})
public class MedicamentCSV {

    private String codeCIS;
    private String denomination;
    private String formePharmaceutique;
    private List<String> voiesAdministration = new ArrayList<>();
    private String statutAdministratifAMM;
    private String typeProcedureAMM;
    private String etatCommercialisation;
    @JsonDeserialize(using = DateDeserializer.class)
    private Date dateAMM;
    private String statutBDM;
    private String numeroAutorisationEuropeenne;
    private List<String> titulaires = new ArrayList<>();
    private String surveillanceRenforcee;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getFormePharmaceutique() {
        return formePharmaceutique;
    }

    public void setFormePharmaceutique(String formePharmaceutique) {
        this.formePharmaceutique = formePharmaceutique;
    }

    public List<String> getVoiesAdministration() {
        return voiesAdministration;
    }

    public void setVoiesAdministration(List<String> voiesAdministration) {
        this.voiesAdministration = voiesAdministration;
    }

    public String getStatutAdministratifAMM() {
        return statutAdministratifAMM;
    }

    public void setStatutAdministratifAMM(String statutAdministratifAMM) {
        this.statutAdministratifAMM = statutAdministratifAMM;
    }

    public String getTypeProcedureAMM() {
        return typeProcedureAMM;
    }

    public void setTypeProcedureAMM(String typeProcedureAMM) {
        this.typeProcedureAMM = typeProcedureAMM;
    }

    public String getEtatCommercialisation() {
        return etatCommercialisation;
    }

    public void setEtatCommercialisation(String etatCommercialisation) {
        this.etatCommercialisation = etatCommercialisation;
    }

    public Date getDateAMM() {
        return dateAMM;
    }

    public void setDateAMM(Date dateAMM) {
        this.dateAMM = dateAMM;
    }

    public String getStatutBDM() {
        return statutBDM;
    }

    public void setStatutBDM(String statutBDM) {
        this.statutBDM = statutBDM;
    }

    public String getNumeroAutorisationEuropeenne() {
        return numeroAutorisationEuropeenne;
    }

    public void setNumeroAutorisationEuropeenne(String numeroAutorisationEuropeenne) {
        this.numeroAutorisationEuropeenne = numeroAutorisationEuropeenne;
    }

    public List<String> getTitulaires() {
        return titulaires;
    }

    public void setTitulaires(List<String> titulaires) {
        this.titulaires = titulaires.stream().map(x -> x.trim()).collect(Collectors.toList());
    }

    public String getSurveillanceRenforcee() {
        return surveillanceRenforcee;
    }

    public void setSurveillanceRenforcee(String surveillanceRenforcee) {
        this.surveillanceRenforcee = surveillanceRenforcee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicamentCSV that = (MedicamentCSV) o;

        return codeCIS.equals(that.codeCIS);

    }

    @Override
    public int hashCode() {
        return codeCIS.hashCode();
    }

    @Override
    public String toString() {
        return "MedicamentCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", denomination='" + denomination + '\'' +
                ", formePharmaceutique='" + formePharmaceutique + '\'' +
                ", voiesAdministration=" + voiesAdministration +
                ", statutAdministratifAMM='" + statutAdministratifAMM + '\'' +
                ", typeProcedureAMM='" + typeProcedureAMM + '\'' +
                ", etatCommercialisation='" + etatCommercialisation + '\'' +
                ", dateAMM='" + dateAMM + '\'' +
                ", statutBDM='" + statutBDM + '\'' +
                ", numeroAutorisationEuropeenne='" + numeroAutorisationEuropeenne + '\'' +
                ", titulaires=" + titulaires +
                ", surveillanceRenforcee='" + surveillanceRenforcee + '\'' +
                '}';
    }
}
