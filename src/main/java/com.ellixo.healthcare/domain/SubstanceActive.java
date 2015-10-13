package com.ellixo.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class SubstanceActive {

    private String designationElementPharmaceutique;
    private String codeSubstance;
    private String denominationSubstance;
    private String dosageSubstance;
    private String referenceDosage;
    @JsonIgnore
    private Integer numero;
    private List<FractionTherapeutique> fractionsTherapeutiques = new ArrayList<>();

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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<FractionTherapeutique> getFractionsTherapeutiques() {
        return fractionsTherapeutiques;
    }

    public void setFractionsTherapeutiques(List<FractionTherapeutique> fractionsTherapeutiques) {
        this.fractionsTherapeutiques = fractionsTherapeutiques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubstanceActive that = (SubstanceActive) o;

        return codeSubstance.equals(that.codeSubstance);

    }

    @Override
    public int hashCode() {
        return codeSubstance.hashCode();
    }

    @Override
    public String toString() {
        return "SubstanceActive{" +
                "designationElementPharmaceutique='" + designationElementPharmaceutique + '\'' +
                ", codeSubstance='" + codeSubstance + '\'' +
                ", denominationSubstance='" + denominationSubstance + '\'' +
                ", dosageSubstance='" + dosageSubstance + '\'' +
                ", referenceDosage='" + referenceDosage + '\'' +
                ", numero=" + numero +
                ", fractionsTherapeutiques=" + fractionsTherapeutiques +
                '}';
    }
}
