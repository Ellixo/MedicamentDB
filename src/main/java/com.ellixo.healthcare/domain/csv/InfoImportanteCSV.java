package com.ellixo.healthcare.domain.csv;

import com.ellixo.healthcare.domain.util.DateDeserializer;
import com.ellixo.healthcare.domain.util.DateHASDeserializer;
import com.ellixo.healthcare.domain.util.DateIIDeserializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonPropertyOrder({"codeCIS", "dateDebut", "dateFin", "info"})
public class InfoImportanteCSV {

    private String codeCIS;
    @JsonDeserialize(using = DateIIDeserializer.class)
    private Date dateDebut;
    @JsonDeserialize(using = DateIIDeserializer.class)
    private Date dateFin;
    private String info;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoImportanteCSV that = (InfoImportanteCSV) o;

        if (!codeCIS.equals(that.codeCIS)) return false;
        if (!dateDebut.equals(that.dateDebut)) return false;
        if (dateFin != null ? !dateFin.equals(that.dateFin) : that.dateFin != null) return false;
        return info.equals(that.info);

    }

    @Override
    public int hashCode() {
        int result = codeCIS.hashCode();
        result = 31 * result + dateDebut.hashCode();
        result = 31 * result + (dateFin != null ? dateFin.hashCode() : 0);
        result = 31 * result + info.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InfoImportanteCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", info='" + info + '\'' +
                '}';
    }
}
