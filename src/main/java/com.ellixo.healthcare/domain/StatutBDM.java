package com.ellixo.healthcare.domain;

public enum StatutBDM {
    RAS(null), ALERTE("Alerte"), WARNING_DISPONIBILITE("Warning disponibilité");

    private String libelle;

    StatutBDM(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static StatutBDM fromCode(String libelle) {
        for (StatutBDM type : values()) {
            if (type.libelle.equalsIgnoreCase(libelle)) {
                return type;
            }
        }
        return null;
    }
}
