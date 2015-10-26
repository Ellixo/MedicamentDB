package com.ellixo.healthcare.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "medicament_db", type = "groupes_generiques")
public class GroupeGenerique {

    @Id
    private String codeGroupe;
    private String libelleGroupe;
    private List<Medicament> medicaments = new ArrayList<>();

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getLibelleGroupe() {
        return libelleGroupe;
    }

    public void setLibelleGroupe(String libelleGroupe) {
        this.libelleGroupe = libelleGroupe;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupeGenerique that = (GroupeGenerique) o;

        return codeGroupe.equals(that.codeGroupe);

    }

    @Override
    public int hashCode() {
        return codeGroupe.hashCode();
    }

    @Override
    public String toString() {
        return "GroupeGenerique{" +
                "codeGroupe='" + codeGroupe + '\'' +
                ", libelleGroupe='" + libelleGroupe + '\'' +
                ", medicaments=" + medicaments +
                '}';
    }

    public static class Medicament {
        private String codeCIS;
        private String denomination;
        private TypeGenerique type;

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

        public TypeGenerique getType() {
            return type;
        }

        public void setType(TypeGenerique type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Medicament that = (Medicament) o;

            return codeCIS.equals(that.codeCIS);

        }

        @Override
        public int hashCode() {
            return codeCIS.hashCode();
        }

        @Override
        public String toString() {
            return "Medicament{" +
                    "codeCIS='" + codeCIS + '\'' +
                    ", denomination='" + denomination + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
