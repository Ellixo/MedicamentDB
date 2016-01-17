package com.ellixo.healthcare.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "medicament_db", type = "interactions_medicamenteuses")
public class Interaction {

    @Id
    private String id;
    private String id1;
    private String famille1;
    private String id2;
    private String famille2;
    private String description;
    private String type;
    private String conseil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getFamille1() {
        return famille1;
    }

    public void setFamille1(String famille1) {
        this.famille1 = famille1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getFamille2() {
        return famille2;
    }

    public void setFamille2(String famille2) {
        this.famille2 = famille2;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interaction that = (Interaction) o;

        if (id1 != null ? !id1.equals(that.id1) : that.id1 != null) return false;
        if (famille1 != null ? !famille1.equals(that.famille1) : that.famille1 != null) return false;
        if (id2 != null ? !id2.equals(that.id2) : that.id2 != null) return false;
        return !(famille2 != null ? !famille2.equals(that.famille2) : that.famille2 != null);

    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (famille1 != null ? famille1.hashCode() : 0);
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        result = 31 * result + (famille2 != null ? famille2.hashCode() : 0);
        return result;
    }
}