package com.ellixo.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class SubstanceActive {
    ;
    private String codeSubstance;
    private String denominationSubstance;
    private String dosageSubstance;
    @JsonIgnore
    private Integer numero;
    private List<FractionTherapeutique> fractionsTherapeutiques = new ArrayList<>();

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
                "codeSubstance='" + codeSubstance + '\'' +
                ", denominationSubstance='" + denominationSubstance + '\'' +
                ", dosageSubstance='" + dosageSubstance + '\'' +
                ", numero=" + numero +
                ", fractionsTherapeutiques=" + fractionsTherapeutiques +
                '}';
    }
}
