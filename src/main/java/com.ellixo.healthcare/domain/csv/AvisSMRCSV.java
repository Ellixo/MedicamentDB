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
