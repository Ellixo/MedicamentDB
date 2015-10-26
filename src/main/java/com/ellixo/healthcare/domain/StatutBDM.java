package com.ellixo.healthcare.domain;

import com.google.common.base.Strings;

public enum StatutBDM {
    RAS(""), ALERTE("Alerte"), WARNING_DISPONIBILITE("Warning disponibilité");

    private String libelle;

    StatutBDM(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static StatutBDM fromCode(String libelle) {
        if (Strings.isNullOrEmpty(libelle)) {
            return RAS;
        }

        for (StatutBDM type : values()) {
            if (type.libelle.equalsIgnoreCase(libelle)) {
                return type;
            }
        }
        return null;
    }
}
