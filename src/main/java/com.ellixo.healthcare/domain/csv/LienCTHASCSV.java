package com.ellixo.healthcare.domain.csv;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"codeDossierHAS", "urlHAS"})
public class LienCTHASCSV {

    private String codeDossierHAS;
    private String urlHAS;

    public String getCodeDossierHAS() {
        return codeDossierHAS;
    }

    public void setCodeDossierHAS(String codeDossierHAS) {
        this.codeDossierHAS = codeDossierHAS;
    }

    public String getUrlHAS() {
        return urlHAS;
    }

    public void setUrlHAS(String urlHAS) {
        this.urlHAS = urlHAS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LienCTHASCSV lienCTHAS = (LienCTHASCSV) o;

        if (!codeDossierHAS.equals(lienCTHAS.codeDossierHAS)) return false;
        return urlHAS.equals(lienCTHAS.urlHAS);

    }

    @Override
    public int hashCode() {
        int result = codeDossierHAS.hashCode();
        result = 31 * result + urlHAS.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LienCTHASCSV{" +
                "codeDossierHAS='" + codeDossierHAS + '\'' +
                ", urlHAS='" + urlHAS + '\'' +
                '}';
    }
}
