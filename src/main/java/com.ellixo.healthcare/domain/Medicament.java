package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.domain.util.DateESDeserializer;
import com.ellixo.healthcare.domain.util.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

@Document(indexName = "medicament_db", type = "medicaments")
public class Medicament {

    @Id
    private String codeCIS;
    private String denomination;
    private String formePharmaceutique;
    private List<String> voiesAdministration = new ArrayList<>();
    private String statutAdministratifAMM;
    private String typeProcedureAMM;
    private Boolean etatCommercialisation;
    @Field(
            type = Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd"
    )
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateESDeserializer.class)
    private Date dateAMM;
    private StatutBDM statutBDM;
    private String numeroAutorisationEuropeenne;
    private List<String> titulaires = new ArrayList<>();
    private Boolean surveillanceRenforcee;

    private List<Presentation> presentations = new ArrayList<>();
    private List<SubstanceActive> composition = new ArrayList<>();
    private List<AvisSMR> avisSMR = new ArrayList<>();
    private List<AvisASMR> avisASMR = new ArrayList<>();
    private List<String> conditionsPrescriptionDelivranceCSV = new ArrayList<>();
    private InfosGenerique infosGenerique;
    private List<InfoImportante> infosImportantes = new ArrayList<>();

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

    public String getFormePharmaceutique() {
        return formePharmaceutique;
    }

    public void setFormePharmaceutique(String formePharmaceutique) {
        this.formePharmaceutique = formePharmaceutique;
    }

    public List<String> getVoiesAdministration() {
        return voiesAdministration;
    }

    public void setVoiesAdministration(List<String> voiesAdministration) {
        this.voiesAdministration = voiesAdministration;
    }

    public String getStatutAdministratifAMM() {
        return statutAdministratifAMM;
    }

    public void setStatutAdministratifAMM(String statutAdministratifAMM) {
        this.statutAdministratifAMM = statutAdministratifAMM;
    }

    public String getTypeProcedureAMM() {
        return typeProcedureAMM;
    }

    public void setTypeProcedureAMM(String typeProcedureAMM) {
        this.typeProcedureAMM = typeProcedureAMM;
    }

    public Boolean getEtatCommercialisation() {
        return etatCommercialisation;
    }

    public void setEtatCommercialisation(Boolean etatCommercialisation) {
        this.etatCommercialisation = etatCommercialisation;
    }

    public Date getDateAMM() {
        return dateAMM;
    }

    public void setDateAMM(Date dateAMM) {
        this.dateAMM = dateAMM;
    }

    public StatutBDM getStatutBDM() {
        return statutBDM;
    }

    public void setStatutBDM(StatutBDM statutBDM) {
        this.statutBDM = statutBDM;
    }

    public String getNumeroAutorisationEuropeenne() {
        return numeroAutorisationEuropeenne;
    }

    public void setNumeroAutorisationEuropeenne(String numeroAutorisationEuropeenne) {
        this.numeroAutorisationEuropeenne = numeroAutorisationEuropeenne;
    }

    public List<String> getTitulaires() {
        return titulaires;
    }

    public void setTitulaires(List<String> titulaires) {
        this.titulaires = titulaires;
    }

    public Boolean getSurveillanceRenforcee() {
        return surveillanceRenforcee;
    }

    public void setSurveillanceRenforcee(Boolean surveillanceRenforcee) {
        this.surveillanceRenforcee = surveillanceRenforcee;
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }

    public List<SubstanceActive> getComposition() {
        return composition;
    }

    public void setComposition(List<SubstanceActive> composition) {
        this.composition = composition;
    }

    public List<AvisSMR> getAvisSMR() {
        return avisSMR;
    }

    public void setAvisSMR(List<AvisSMR> avisSMR) {
        this.avisSMR = avisSMR;
    }

    public List<AvisASMR> getAvisASMR() {
        return avisASMR;
    }

    public void setAvisASMR(List<AvisASMR> avisASMR) {
        this.avisASMR = avisASMR;
    }

    public List<String> getConditionsPrescriptionDelivranceCSV() {
        return conditionsPrescriptionDelivranceCSV;
    }

    public void setConditionsPrescriptionDelivranceCSV(List<String> conditionsPrescriptionDelivranceCSV) {
        this.conditionsPrescriptionDelivranceCSV = conditionsPrescriptionDelivranceCSV;
    }

    public InfosGenerique getInfosGenerique() {
        return infosGenerique;
    }

    public void setInfosGenerique(InfosGenerique infosGenerique) {
        this.infosGenerique = infosGenerique;
    }

    public List<InfoImportante> getInfosImportantes() {
        return infosImportantes;
    }

    public void setInfosImportantes(List<InfoImportante> infosImportantes) {
        this.infosImportantes = infosImportantes;
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
                ", formePharmaceutique='" + formePharmaceutique + '\'' +
                ", voiesAdministration=" + voiesAdministration +
                ", statutAdministratifAMM='" + statutAdministratifAMM + '\'' +
                ", typeProcedureAMM='" + typeProcedureAMM + '\'' +
                ", etatCommercialisation='" + etatCommercialisation + '\'' +
                ", dateAMM=" + dateAMM +
                ", statutBDM='" + statutBDM + '\'' +
                ", numeroAutorisationEuropeenne='" + numeroAutorisationEuropeenne + '\'' +
                ", titulaires=" + titulaires +
                ", surveillanceRenforcee='" + surveillanceRenforcee + '\'' +
                ", presentations=" + presentations +
                ", composition=" + composition +
                ", avisSMR=" + avisSMR +
                ", avisASMR=" + avisASMR +
                ", conditionsPrescriptionDelivranceCSV=" + conditionsPrescriptionDelivranceCSV +
                ", infosGenerique=" + infosGenerique +
                ", infosImportantes=" + infosImportantes +
                '}';
    }

    public static class InfosGenerique {

        private TypeGenerique type;
        private String codeGroupe;
        private String libelleGroupe;
        private List<MedicamentGenerique> autresMedicamentsGroupe = new ArrayList<>();

        public TypeGenerique getType() {
            return type;
        }

        public void setType(TypeGenerique type) {
            this.type = type;
        }

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

        public List<MedicamentGenerique> getAutresMedicamentsGroupe() {
            return autresMedicamentsGroupe;
        }

        public void setAutresMedicamentsGroupe(List<MedicamentGenerique> autresMedicamentsGroupe) {
            this.autresMedicamentsGroupe = autresMedicamentsGroupe;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InfosGenerique that = (InfosGenerique) o;

            if (type != that.type) return false;
            return codeGroupe.equals(that.codeGroupe);

        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + codeGroupe.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "InfosGenerique{" +
                    "type=" + type +
                    ", codeGroupe='" + codeGroupe + '\'' +
                    ", libelleGroupe='" + libelleGroupe + '\'' +
                    ", autresMedicamentsGroupe=" + autresMedicamentsGroupe +
                    '}';
        }

        public static class MedicamentGenerique {
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

                MedicamentGenerique that = (MedicamentGenerique) o;

                return codeCIS.equals(that.codeCIS);

            }

            @Override
            public int hashCode() {
                return codeCIS.hashCode();
            }

            @Override
            public String toString() {
                return "MedicamentGenerique{" +
                        "codeCIS='" + codeCIS + '\'' +
                        ", denomination='" + denomination + '\'' +
                        ", type=" + type +
                        '}';
            }
        }
    }

    public static class InfoImportante {

        @Field(
                type = FieldType.Date,
                index = FieldIndex.not_analyzed,
                store = true,
                format = DateFormat.custom, pattern = "yyyy-MM-dd"
        )
        @JsonSerialize(using = DateSerializer.class)
        @JsonDeserialize(using = DateESDeserializer.class)
        private Date dateDebut;
        @Field(
                type = FieldType.Date,
                index = FieldIndex.not_analyzed,
                store = true,
                format = DateFormat.custom, pattern = "yyyy-MM-dd"
        )
        @JsonSerialize(using = DateSerializer.class)
        @JsonDeserialize(using = DateESDeserializer.class)
        private Date dateFin;
        private String infoURL;
        private String infoLibelle;

        public Date getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
        }

        public Date getDateFin() {
            return dateFin;
        }

        public void setDateFin(Date dateFin) {
            this.dateFin = dateFin;
        }

        public String getInfoURL() {
            return infoURL;
        }

        public void setInfoURL(String infoURL) {
            this.infoURL = infoURL;
        }

        public String getInfoLibelle() {
            return infoLibelle;
        }

        public void setInfoLibelle(String infoLibelle) {
            this.infoLibelle = infoLibelle;
        }

        @Override
        public String toString() {
            return "InfoImportante{" +
                    ", dateDebut=" + dateDebut +
                    ", dateFin=" + dateFin +
                    ", infoURL='" + infoURL + '\'' +
                    ", infoLibelle='" + infoLibelle + '\'' +
                    '}';
        }
    }
}
