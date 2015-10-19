package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.domain.util.DateESDeserializer;
import com.ellixo.healthcare.domain.util.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

public class Presentation {

    private String codeCIP7;
    private String libelle;
    private String statutAdministratif;
    private String etatCommercialisationAMM;
    @Field(
            type = Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd"
    )
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateESDeserializer.class)
    private Date dateDeclarationCommercialisation;
    private String codeCIP13;
    private Boolean agrementCollectivites;
    private List<String> tauxRemboursement = new ArrayList<>();
    private Float prix;
    private String indicationsRemboursement;

    public String getCodeCIP7() {
        return codeCIP7;
    }

    public void setCodeCIP7(String codeCIP7) {
        this.codeCIP7 = codeCIP7;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStatutAdministratif() {
        return statutAdministratif;
    }

    public void setStatutAdministratif(String statutAdministratif) {
        this.statutAdministratif = statutAdministratif;
    }

    public String getEtatCommercialisationAMM() {
        return etatCommercialisationAMM;
    }

    public void setEtatCommercialisationAMM(String etatCommercialisationAMM) {
        this.etatCommercialisationAMM = etatCommercialisationAMM;
    }

    public Date getDateDeclarationCommercialisation() {
        return dateDeclarationCommercialisation;
    }

    public void setDateDeclarationCommercialisation(Date dateDeclarationCommercialisation) {
        this.dateDeclarationCommercialisation = dateDeclarationCommercialisation;
    }

    public String getCodeCIP13() {
        return codeCIP13;
    }

    public void setCodeCIP13(String codeCIP13) {
        this.codeCIP13 = codeCIP13;
    }

    public Boolean getAgrementCollectivites() {
        return agrementCollectivites;
    }

    public void setAgrementCollectivites(Boolean agrementCollectivites) {
        this.agrementCollectivites = agrementCollectivites;
    }

    public List<String> getTauxRemboursement() {
        return tauxRemboursement;
    }

    public void setTauxRemboursement(List<String> tauxRemboursement) {
        this.tauxRemboursement = tauxRemboursement;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getIndicationsRemboursement() {
        return indicationsRemboursement;
    }

    public void setIndicationsRemboursement(String indicationsRemboursement) {
        this.indicationsRemboursement = indicationsRemboursement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Presentation that = (Presentation) o;

        return codeCIP7.equals(that.codeCIP7);

    }

    @Override
    public int hashCode() {
        return codeCIP7.hashCode();
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "codeCIP7='" + codeCIP7 + '\'' +
                ", libelle='" + libelle + '\'' +
                ", statutAdministratif='" + statutAdministratif + '\'' +
                ", etatCommercialisationAMM='" + etatCommercialisationAMM + '\'' +
                ", dateDeclarationCommercialisation=" + dateDeclarationCommercialisation +
                ", codeCIP13='" + codeCIP13 + '\'' +
                ", agrementCollectivites='" + agrementCollectivites + '\'' +
                ", tauxRemboursement=" + tauxRemboursement +
                ", prix=" + prix +
                ", indicationsRemboursement='" + indicationsRemboursement + '\'' +
                '}';
    }
}
