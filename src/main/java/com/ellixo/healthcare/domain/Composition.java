package com.ellixo.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Composition {

    private String designationElementPharmaceutique;
    private String referenceDosage;

    private List<SubstanceActive> substancesActives = new ArrayList<>();

    public String getDesignationElementPharmaceutique() {
        return designationElementPharmaceutique;
    }

    public void setDesignationElementPharmaceutique(String designationElementPharmaceutique) {
        this.designationElementPharmaceutique = designationElementPharmaceutique;
    }

    public String getReferenceDosage() {
        return referenceDosage;
    }

    public void setReferenceDosage(String referenceDosage) {
        this.referenceDosage = referenceDosage;
    }

    public List<SubstanceActive> getSubstancesActives() {
        return substancesActives;
    }

    public void setSubstancesActives(List<SubstanceActive> substancesActives) {
        this.substancesActives = substancesActives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        if (!designationElementPharmaceutique.equals(that.designationElementPharmaceutique)) return false;
        return referenceDosage.equals(that.referenceDosage);

    }

    @Override
    public int hashCode() {
        int result = designationElementPharmaceutique.hashCode();
        result = 31 * result + referenceDosage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "designationElementPharmaceutique='" + designationElementPharmaceutique + '\'' +
                ", referenceDosage='" + referenceDosage + '\'' +
                ", substancesActives=" + substancesActives +
                '}';
    }
}
