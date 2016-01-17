package com.ellixo.healthcare.domain;

import java.util.ArrayList;
import java.util.List;

public class SubstanceInteraction {

    private String substance;
    private List<String> interactions = new ArrayList<>();
    private List<String> familles = new ArrayList<>();

    public List<String> getFamilles() {
        return familles;
    }

    public void setFamilles(List<String> familles) {
        this.familles = familles;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public List<String> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<String> interactions) {
        this.interactions = interactions;
    }
}