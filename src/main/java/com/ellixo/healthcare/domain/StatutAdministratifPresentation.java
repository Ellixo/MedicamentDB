package com.ellixo.healthcare.domain;

import com.google.common.base.Strings;

public enum StatutAdministratifPresentation {
    ACTIVE("Présentation active"), ABROGEE("Présentation abrogée");

    private String libelle;

    StatutAdministratifPresentation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static StatutAdministratifPresentation fromCode(String libelle) {
        if (Strings.isNullOrEmpty(libelle)) {
            return null;
        }

        for (StatutAdministratifPresentation type : values()) {
            if (type.libelle.equalsIgnoreCase(libelle.trim())) {
                return type;
            }
        }
        return null;
    }
}
