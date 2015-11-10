/*
 * Open Medicaments
 * Copyright (C) 2015  Ellixo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ellixo.healthcare.domain.util;

import com.ellixo.healthcare.domain.*;
import com.ellixo.healthcare.domain.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ESMapperDecorator implements ESMapper {

    @Autowired
    @Qualifier("delegate")
    private ESMapper delegate;

    public Medicament toMedicamentES(MedicamentCSV csv) {
        Medicament medicament = delegate.toMedicamentES(csv);
        // etat
        String etat = csv.getEtatCommercialisation();
        medicament.setEtatCommercialisation(etat != null && etat.equals("Commercialisée"));
        // homeopathie
        if (medicament.getTypeProcedureAMM() != null) {
            medicament.setHomeopathie(medicament.getTypeProcedureAMM().equalsIgnoreCase("Enreg homéo (Proc. Nat.)"));
        }

        return medicament;
    }

    @Override
    public List<Medicament> toMedicamentES(List<MedicamentCSV> csv) {
        if (csv == null) {
            return null;
        }

        List<Medicament> list = new ArrayList<>();
        for (MedicamentCSV medicamentCSV : csv) {
            list.add(toMedicamentES(medicamentCSV));
        }
        return list;
    }

    @Override
    public Presentation toPresentationES(PresentationCSV csv) {
        return delegate.toPresentationES(csv);
    }

    @Override
    public SubstanceActive toSubstanceActiveES(CompositionCSV csv) {
        return delegate.toSubstanceActiveES(csv);
    }

    @Override
    public FractionTherapeutique toFractionTherapeutiqueES(CompositionCSV csv) {
        return delegate.toFractionTherapeutiqueES(csv);
    }

    @Override
    public AvisSMR toAvisSMRES(AvisSMRCSV csv) {
        return delegate.toAvisSMRES(csv);
    }

    @Override
    public AvisASMR toAvisASMRES(AvisASMRCSV csv) {
        return delegate.toAvisASMRES(csv);
    }
}
