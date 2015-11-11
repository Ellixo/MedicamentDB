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
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BooleanMapper.class, StatutBDMMapper.class, StatutAdministratifPresentationMapper.class})
@DecoratedWith(ESMapperDecorator.class)
public interface ESMapper {

    @Mappings({
            @Mapping(target = "etatCommercialisation", ignore = true)
    })
    Medicament toMedicamentES(MedicamentCSV csv);

    List<Medicament> toMedicamentES(List<MedicamentCSV> csv);

    Presentation toPresentationES(PresentationCSV csv);

    SubstanceActive toSubstanceActiveES(CompositionCSV csv);

    FractionTherapeutique toFractionTherapeutiqueES(CompositionCSV csv);

    AvisSMR toAvisSMRES(AvisSMRCSV csv);

    AvisASMR toAvisASMRES(AvisASMRCSV csv);

    MedicamentExtract toExtract(Medicament medicament);

    List<MedicamentExtract> toExtractList(List<Medicament> medicaments);

}
