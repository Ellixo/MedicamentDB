package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.domain.util.DateESDeserializer;
import com.ellixo.healthcare.domain.util.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import org.springframework.data.annotation.Id;

@Document(indexName = "medicament_db", type = "infos_importantes")
public class InfoImportante {

    @Id
    private String id;
    private String codeCIS;
    @Field(
            type = FieldType.Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "yyyy/MM/dd"
    )
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateESDeserializer.class)
    private Date dateDebut;
    @Field(
            type = FieldType.Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "yyyy/MM/dd"
    )
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateESDeserializer.class)
    private Date dateFin;
    private String infoURL;
    private String infoLibelle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoImportante that = (InfoImportante) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "InfoImportante{" +
                "id='" + id + '\'' +
                ", codeCIS='" + codeCIS + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", infoURL='" + infoURL + '\'' +
                ", infoLibelle='" + infoLibelle + '\'' +
                '}';
    }
}
