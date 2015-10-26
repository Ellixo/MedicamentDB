package com.ellixo.healthcare.domain.csv;

import com.ellixo.healthcare.domain.util.DateHASDeserializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonPropertyOrder({"codeCIS", "condition"})
public class ConditionPrescriptionDelivranceCSV {

    private String codeCIS;
    private String condition;

    public String getCodeCIS() {
        return codeCIS;
    }

    public void setCodeCIS(String codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionPrescriptionDelivranceCSV that = (ConditionPrescriptionDelivranceCSV) o;

        if (!codeCIS.equals(that.codeCIS)) return false;
        return condition.equals(that.condition);

    }

    @Override
    public int hashCode() {
        int result = codeCIS.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ConditionPrescriptionDelivranceCSV{" +
                "codeCIS='" + codeCIS + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
