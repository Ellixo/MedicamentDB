package com.ellixo.healthcare.domain.util;

import com.ellixo.healthcare.domain.StatutAdministratifPresentation;
import com.ellixo.healthcare.domain.StatutBDM;
import org.springframework.stereotype.Component;

@Component
public class StatutAdministratifPresentationMapper {

    public String asString(StatutAdministratifPresentation statut) {
        if (statut == null) {
            return null;
        }
        return statut.getLibelle();
    }

    public StatutAdministratifPresentation asStatut(String string) {
        if (string == null) {
            return null;
        }

        return StatutAdministratifPresentation.fromCode(string);
    }

}
