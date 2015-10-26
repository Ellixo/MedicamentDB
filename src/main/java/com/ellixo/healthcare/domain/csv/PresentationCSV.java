package com.ellixo.healthcare.domain.csv;

import com.ellixo.healthcare.domain.util.DateDeserializer;
import com.ellixo.healthcare.domain.util.FloatDeserializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"codeCIS", "codeCIP7", "libelle", "statutAdministratif", "etatCommercialisationAMM", "dateDeclarationCommercialisation", "codeCIP13", "agrementCollectivites", "tauxRemboursement", "prix", "indicationsRemboursement"})
public class PresentationCSV {

    private String codeCIS;
    private String codeCIP7;
    private String libelle;
    private String statutAdministratif;
    private String etatCommercialisationAMM;
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date dateDeclarationCommercialisation;
    private String codeCIP13;
    private String agrementCollectivites;
    private List<String> tauxRemboursement = new ArrayList<>();
    @JsonDeserialize(using = FloatDeserializer.class)
    private Float prix;
    private String indicationsRemboursement;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

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

    public java.util.Date getDateDeclarationCommercialisation() {
        return dateDeclarationCommercialisation;
    }

    public void setDateDeclarationCommercialisation(java.util.Date dateDeclarationCommercialisation) {
        this.dateDeclarationCommercialisation = dateDeclarationCommercialisation;
    }

    public String getCodeCIP13() {
        return codeCIP13;
    }

    public void setCodeCIP13(String codeCIP13) {
        this.codeCIP13 = codeCIP13;
    }

    public String getAgrementCollectivites() {
        return agrementCollectivites;
    }

    public void setAgrementCollectivites(String agrementCollectivites) {
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

        PresentationCSV that = (PresentationCSV) o;

        return codeCIS.equals(that.codeCIS);

    }

    @Override
    public int hashCode() {
        return codeCIS.hashCode();
    }

    @Override
    public String toString() {
        return "PresentationCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", codeCIP7='" + codeCIP7 + '\'' +
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
