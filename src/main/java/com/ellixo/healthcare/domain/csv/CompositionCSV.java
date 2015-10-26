package com.ellixo.healthcare.domain.csv;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"codeCIS", "designationElementPharmaceutique", "codeSubstance", "denominationSubstance", "dosageSubstance", "referenceDosage", "natureComposant", "numero"})
public class CompositionCSV {

    private String codeCIS;
    private String designationElementPharmaceutique;
    private String codeSubstance;
    private String denominationSubstance;
    private String dosageSubstance;
    private String referenceDosage;
    private String natureComposant;
    private Integer numero;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getDesignationElementPharmaceutique() {
        return designationElementPharmaceutique;
    }

    public void setDesignationElementPharmaceutique(String designationElementPharmaceutique) {
        this.designationElementPharmaceutique = designationElementPharmaceutique;
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

    public String getReferenceDosage() {
        return referenceDosage;
    }

    public void setReferenceDosage(String referenceDosage) {
        this.referenceDosage = referenceDosage;
    }

    public String getNatureComposant() {
        return natureComposant;
    }

    public void setNatureComposant(String natureComposant) {
        this.natureComposant = natureComposant;
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

        CompositionCSV that = (CompositionCSV) o;

        return codeCIS.equals(that.codeCIS);

    }

    @Override
    public int hashCode() {
        return codeCIS.hashCode();
    }

    @Override
    public String toString() {
        return "CompositionCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", designationElementPharmaceutique='" + designationElementPharmaceutique + '\'' +
                ", codeSubstance='" + codeSubstance + '\'' +
                ", denominationSubstance='" + denominationSubstance + '\'' +
                ", dosageSubstance='" + dosageSubstance + '\'' +
                ", referenceDosage='" + referenceDosage + '\'' +
                ", natureComposant='" + natureComposant + '\'' +
                ", numero=" + numero +
                '}';
    }
}
