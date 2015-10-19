package com.ellixo.healthcare.domain.util;

import com.ellixo.healthcare.domain.StatutBDM;
import org.springframework.stereotype.Component;

@Component
public class StatutBDMMapper {

    public String asString(StatutBDM statut) {
        if (statut == null) {
            return null;
        }
        return statut.getLibelle();
    }

    public StatutBDM asStatut(String string) {
        if (string == null) {
            return null;
        }

        return StatutBDM.fromCode(string);
    }

}
