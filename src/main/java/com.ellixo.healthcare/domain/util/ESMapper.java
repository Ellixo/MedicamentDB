package com.ellixo.healthcare.domain.util;

import com.ellixo.healthcare.domain.*;
import com.ellixo.healthcare.domain.csv.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BooleanMapper.class})
public interface ESMapper {

    Medicament toMedicamentES(MedicamentCSV csv);

    List<Medicament> toMedicamentES(List<MedicamentCSV> csv);

    Presentation toPresentationES(PresentationCSV csv);

    SubstanceActive toSubstanceActiveES(CompositionCSV csv);

    FractionTherapeutique toFractionTherapeutiqueES(CompositionCSV csv);

    AvisSMR toAvisSMRES(AvisSMRCSV csv);

    AvisASMR toAvisASMRES(AvisASMRCSV csv);

}
