package com.ellixo.healthcare.domain;

public class InteractionEntreMedicaments {

    private String codeCISMedicament1;
    private String nomMedicament1;
    private String codeCISMedicament2;
    private String nomMedicament2;
    private String description;
    private String type;
    private String conseil;

    public InteractionEntreMedicaments() {
    }

    public InteractionEntreMedicaments(String codeCISMedicament1, String nomMedicament1, String codeCISMedicament2, String nomMedicament2, String description, String type, String conseil) {
        this.codeCISMedicament1 = codeCISMedicament1;
        this.nomMedicament1 = nomMedicament1;
        this.codeCISMedicament2 = codeCISMedicament2;
        this.nomMedicament2 = nomMedicament2;
        this.description = description;
        this.type = type;
        this.conseil = conseil;
    }

    public String getCodeCISMedicament1() {
        return codeCISMedicament1;
    }

    public void setCodeCISMedicament1(String codeCISMedicament1) {
        this.codeCISMedicament1 = codeCISMedicament1;
    }

    public String getNomMedicament1() {
        return nomMedicament1;
    }

    public void setNomMedicament1(String nomMedicament1) {
        this.nomMedicament1 = nomMedicament1;
    }

    public String getCodeCISMedicament2() {
        return codeCISMedicament2;
    }

    public void setCodeCISMedicament2(String codeCISMedicament2) {
        this.codeCISMedicament2 = codeCISMedicament2;
    }

    public String getNomMedicament2() {
        return nomMedicament2;
    }

    public void setNomMedicament2(String nomMedicament2) {
        this.nomMedicament2 = nomMedicament2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConseil() {
        return conseil;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }
}
