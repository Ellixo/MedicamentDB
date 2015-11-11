package com.ellixo.healthcare.domain;

public class MedicamentExtract {

    private String codeCIS;
    private String denomination;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicamentExtract that = (MedicamentExtract) o;

        return codeCIS.equals(that.codeCIS);

    }

    @Override
    public int hashCode() {
        return codeCIS.hashCode();
    }

    @Override
    public String toString() {
        return "MedicamentExtract{" +
                "codeCIS='" + codeCIS + '\'' +
                ", denomination='" + denomination + '\'' +
                '}';
    }
}
