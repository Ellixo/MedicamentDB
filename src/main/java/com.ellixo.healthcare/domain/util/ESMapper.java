package com.ellixo.healthcare.domain.util;

import com.ellixo.healthcare.domain.*;
import com.ellixo.healthcare.domain.csv.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BooleanMapper.class, StatutBDMMapper.class})
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

}
