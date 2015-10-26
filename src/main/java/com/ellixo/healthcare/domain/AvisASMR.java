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

public class AvisASMR {

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

        AvisASMR avisASMR = (AvisASMR) o;

        return codeDossierHAS.equals(avisASMR.codeDossierHAS);

    }

    @Override
    public int hashCode() {
        return codeDossierHAS.hashCode();
    }

    @Override
    public String toString() {
        return "AvisASMR{" +
                "codeDossierHAS='" + codeDossierHAS + '\'' +
                ", motifEvaluation='" + motifEvaluation + '\'' +
                ", dateAvisCommissionTransparence=" + dateAvisCommissionTransparence +
                ", valeurSMR='" + valeurSMR + '\'' +
                ", libelleSMR='" + libelleSMR + '\'' +
                '}';
    }
}
