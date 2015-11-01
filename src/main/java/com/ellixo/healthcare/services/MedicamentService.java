package com.ellixo.healthcare.services;

import com.ellixo.healthcare.Constants;
import com.ellixo.healthcare.domain.*;
import com.ellixo.healthcare.domain.csv.*;
import com.ellixo.healthcare.domain.util.ESMapper;
import com.ellixo.healthcare.repository.GroupeGeneriqueRepository;
import com.ellixo.healthcare.repository.InfoImportanteRepository;
import com.ellixo.healthcare.repository.MedicamentRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.Strings;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedicamentService {

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private MedicamentRepository repositoryMedicaments;
    @Autowired
    private GroupeGeneriqueRepository repositoryGroupesGeneriques;
    @Autowired
    private InfoImportanteRepository repositoryInfosImportantes;

    @Autowired
    private ESMapper mapper;

    public Triple<List<Medicament>, List<GroupeGenerique>, List<InfoImportante>> readMedicaments(File dir) {
        try {
            List<Medicament> medicaments = initMedicaments(dir);

            linkPresentations(dir, medicaments);
            linkCompositions(dir, medicaments);
            linkConditionsPrescriptionDelivrance(dir, medicaments);

            Map<String, String> urlsHAS = initUrlsHAS(dir);
            linkAvisSMR(dir, medicaments, urlsHAS);
            linkAvisASMR(dir, medicaments, urlsHAS);

            List<GroupeGenerique> groupesGeneriques = initGroupesGeneriques(dir, medicaments);

            List<InfoImportante> infosImportantes = initInfosImportantes(dir, medicaments);

            return Triple.of(medicaments, groupesGeneriques, infosImportantes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDB(File dir) {
        Triple<List<Medicament>, List<GroupeGenerique>, List<InfoImportante>> triple = readMedicaments(dir);

        repositoryMedicaments.save(triple.getLeft());
        repositoryGroupesGeneriques.save(triple.getMiddle());
        repositoryInfosImportantes.save(triple.getRight());
    }

    private List<Medicament> initMedicaments(File dir) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(MedicamentCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<MedicamentCSV> it = csvMapper.readerFor(MedicamentCSV.class).with(schema).readValues(new File(dir, Constants.CIS_BDPM_FILE));

        return mapper.toMedicamentES(it.readAll());
    }

    private void linkPresentations(File dir, List<Medicament> medicaments) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(PresentationCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<PresentationCSV> it = csvMapper.readerFor(PresentationCSV.class).with(schema).readValues(new File(dir, Constants.CIS_CIP_BDPM_FILE));

        Medicament medicament;
        while (it.hasNext()) {
            PresentationCSV presentation = it.next();

            medicament = medicaments.stream()
                    .filter(x -> x.getCodeCIS().equals(presentation.getCodeCIS()))
                    .findFirst().orElse(null);

            if (medicament != null) {
                medicament.getPresentations().add(mapper.toPresentationES(presentation));
            }
        }
    }

    private void linkCompositions(File dir, List<Medicament> medicaments) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(CompositionCSV.class).withColumnSeparator('\t');
        MappingIterator<CompositionCSV> it = csvMapper.readerFor(CompositionCSV.class).with(schema).readValues(new File(dir, Constants.CIS_COMPO_BDPM_FILE));

        String previousCodeCIS = null;
        CompositionCSV composition;
        List<CompositionCSV> compositions = new ArrayList<>();
        while (it.hasNext()) {
            composition = it.next();

            if (previousCodeCIS != null && !composition.getCodeCIS().equals(previousCodeCIS)) {
                linkCompositions(previousCodeCIS, compositions, medicaments);

                compositions = new ArrayList<>();
            }

            compositions.add(composition);

            previousCodeCIS = composition.getCodeCIS();
        }

        if (previousCodeCIS != null) {
            linkCompositions(previousCodeCIS, compositions, medicaments);
        }
    }

    private void linkCompositions(String codeCIS, List<CompositionCSV> compositions, List<Medicament> medicaments) {
        Medicament medicament = medicaments.stream()
                .filter(x -> x.getCodeCIS().equals(codeCIS))
                .findFirst().orElse(null);

        if (medicament != null) {
            Map<String, List<CompositionCSV>> map = compositions.stream().collect(
                    Collectors.groupingBy(CompositionCSV::getDesignationElementPharmaceutique)
            );

            Composition composition;
            List<CompositionCSV> csv;
            for (Map.Entry<String, List<CompositionCSV>> element : map.entrySet()) {
                composition = new Composition();
                composition.setDesignationElementPharmaceutique(element.getKey());

                csv = element.getValue();
                csv.sort((o1, o2) -> {
                    int sort = -(o1.getNatureComposant().compareTo(o2.getNatureComposant()));
                    if (sort != 0) {
                        return sort;
                    } else {
                        return Integer.compare(o1.getNumero(), o2.getNumero());
                    }
                });

                for (CompositionCSV valeur : csv) {
                    if (Strings.isNullOrEmpty(composition.getReferenceDosage())) {
                        composition.setReferenceDosage(valeur.getReferenceDosage());
                    }

                    if (valeur.getNatureComposant().equals("SA")) {
                        composition.getSubstancesActives().add(mapper.toSubstanceActiveES(valeur));
                    } else {
                        FractionTherapeutique ft = mapper.toFractionTherapeutiqueES(valeur);
                        composition.getSubstancesActives().stream()
                                .filter(sa -> sa.getNumero().equals(ft.getNumero())).findFirst().get().getFractionsTherapeutiques().add(ft);
                    }
                }

                medicament.getCompositions().add(composition);
            }
        }
    }

    private void linkAvisSMR(File dir, List<Medicament> medicaments, Map<String, String> urlsHAS) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(AvisSMRCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<AvisSMRCSV> it = csvMapper.readerFor(AvisSMRCSV.class).with(schema).readValues(new File(dir, Constants.CIS_HAS_SMR_BDPM_FILE));

        Medicament medicament;
        AvisSMR avisSMR;
        while (it.hasNext()) {
            AvisSMRCSV csv = it.next();

            medicament = medicaments.stream()
                    .filter(x -> x.getCodeCIS().equals(csv.getCodeCIS()))
                    .findFirst().orElse(null);

            if (medicament != null) {
                avisSMR = mapper.toAvisSMRES(csv);
                avisSMR.setUrlHAS(urlsHAS.get(avisSMR.getCodeDossierHAS()));
                medicament.getAvisSMR().add(avisSMR);
            }
        }
    }

    private void linkAvisASMR(File dir, List<Medicament> medicaments, Map<String, String> urlsHAS) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(AvisASMRCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<AvisASMRCSV> it = csvMapper.readerFor(AvisASMRCSV.class).with(schema).readValues(new File(dir, Constants.CIS_HAS_ASMR_BDPM_FILE));

        Medicament medicament;
        AvisASMR avisASMR;
        while (it.hasNext()) {
            AvisASMRCSV csv = it.next();

            medicament = medicaments.stream()
                    .filter(x -> x.getCodeCIS().equals(csv.getCodeCIS()))
                    .findFirst().orElse(null);

            if (medicament != null) {
                avisASMR = mapper.toAvisASMRES(csv);
                avisASMR.setUrlHAS(urlsHAS.get(avisASMR.getCodeDossierHAS()));
                medicament.getAvisASMR().add(avisASMR);
            }
        }
    }

    private Map<String, String> initUrlsHAS(File dir) throws IOException {
        Map<String, String> liens = new HashMap<>();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(LienCTHASCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<LienCTHASCSV> it = csvMapper.readerFor(LienCTHASCSV.class).with(schema).readValues(new File(dir, Constants.HAS_LIENS_PAGE_CT_BDPM_FILE));

        while (it.hasNext()) {
            LienCTHASCSV lien = it.next();
            liens.put(lien.getCodeDossierHAS(), lien.getUrlHAS());
        }

        return liens;
    }

    private void linkConditionsPrescriptionDelivrance(File dir, List<Medicament> medicaments) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(ConditionPrescriptionDelivranceCSV.class).withColumnSeparator('\t');
        MappingIterator<ConditionPrescriptionDelivranceCSV> it = csvMapper.readerFor(ConditionPrescriptionDelivranceCSV.class).with(schema).readValues(new File(dir, Constants.CIS_CPD_BDPM_FILE));

        Medicament medicament;
        while (it.hasNext()) {
            ConditionPrescriptionDelivranceCSV condition = it.next();

            medicament = medicaments.stream()
                    .filter(x -> x.getCodeCIS().equals(condition.getCodeCIS()))
                    .findFirst().orElse(null);

            if (medicament != null) {
                medicament.getConditionsPrescriptionDelivrance().add(condition.getCondition());

                medicament.getConditionsPrescriptionDelivrance().sort((o1, o2) -> {
                    if (o1.trim().equals("liste I")) {
                        return -1;
                    } else if (o2.trim().equals("liste I")) {
                        return 1;
                    } else {
                        if (o1.trim().equals("liste II")) {
                            return -1;
                        } else if (o2.trim().equals("liste II")) {
                            return 1;
                        } else {
                            return o1.compareTo(o2);
                        }
                    }
                });
            }
        }
    }

    private List<GroupeGenerique> initGroupesGeneriques(File dir, List<Medicament> medicaments) throws IOException {
        List<GroupeGenerique> groupesGeneriques = new ArrayList<>();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(GroupeGeneriqueCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<GroupeGeneriqueCSV> it = csvMapper.readerFor(GroupeGeneriqueCSV.class).with(schema).readValues(new File(dir, Constants.CIS_GENER_BDPM_FILE));

        GroupeGeneriqueCSV csv;
        String previousCode = null;
        List<GroupeGeneriqueCSV> groupes = new ArrayList<>();
        while (it.hasNext()) {
            csv = it.next();

            if (previousCode != null && !csv.getCodeGroupe().equals(previousCode)) {
                groupesGeneriques.add(handleGroupeGenerique(groupes, medicaments));

                groupes = new ArrayList<>();
            }

            groupes.add(csv);
            previousCode = csv.getCodeGroupe();
        }

        if (previousCode != null) {
            handleGroupeGenerique(groupes, medicaments);
        }

        return groupesGeneriques;
    }

    private GroupeGenerique handleGroupeGenerique(List<GroupeGeneriqueCSV> groupes, List<Medicament> medicaments) {
        groupes.sort((o1, o2) -> o1.getOrdre().compareTo(o2.getOrdre()));

        GroupeGenerique groupeGenerique = new GroupeGenerique();
        groupeGenerique.setCodeGroupe(groupes.get(0).getCodeGroupe());
        groupeGenerique.setLibelleGroupe(groupes.get(0).getLibelleGroupe());

        List<Pair<Medicament, Integer>> familleMedicaments = new ArrayList<>();
        for (GroupeGeneriqueCSV item : groupes) {
            Medicament medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals(item.getCodeCIS())).findFirst().orElse(null);
            if (medicament != null) {
                familleMedicaments.add(Pair.of(medicament, item.getTypeGenerique()));
            }
        }

        GroupeGenerique.Medicament medicamentGG;
        Medicament.InfosGenerique infosGenerique;
        for (Pair<Medicament, Integer> item : familleMedicaments) {
            // groupe
            medicamentGG = new GroupeGenerique.Medicament();
            medicamentGG.setCodeCIS(item.getLeft().getCodeCIS());
            medicamentGG.setDenomination(item.getLeft().getDenomination());
            medicamentGG.setType(TypeGenerique.fromCode(item.getRight()));
            groupeGenerique.getMedicaments().add(medicamentGG);

            // medicament
            infosGenerique = new Medicament.InfosGenerique();
            infosGenerique.setCodeGroupe(groupeGenerique.getCodeGroupe());
            infosGenerique.setLibelleGroupe(groupeGenerique.getLibelleGroupe());
            infosGenerique.setType(medicamentGG.getType());
            infosGenerique.getAutresMedicamentsGroupe().addAll(
                    familleMedicaments.stream()
                            .filter(x -> !x.getLeft().getCodeCIS().equals(item.getLeft().getCodeCIS()))
                            .map(x -> {
                                Medicament.InfosGenerique.MedicamentGenerique generique = new Medicament.InfosGenerique.MedicamentGenerique();
                                generique.setCodeCIS(x.getLeft().getCodeCIS());
                                generique.setDenomination(x.getLeft().getDenomination());
                                generique.setType(TypeGenerique.fromCode(x.getRight()));
                                generique.getPrix().addAll(x.getLeft().getPresentations()
                                        .stream().map(y -> y.getPrix()).collect(Collectors.toSet()));
                                return generique;
                            }).collect(Collectors.toList())
            );
            item.getLeft().setInfosGenerique(infosGenerique);
        }

        return groupeGenerique;
    }

    private List<InfoImportante> initInfosImportantes(File dir, List<Medicament> medicaments) throws IOException {
        List<InfoImportante> infosImportantes = new ArrayList<>();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(InfoImportanteCSV.class).withArrayElementSeparator(';').withColumnSeparator('\t');
        MappingIterator<InfoImportanteCSV> it = csvMapper.readerFor(InfoImportanteCSV.class).with(schema).readValues(new File(dir, Constants.CIS_INFO_IMPORTANTES_FILE));

        InfoImportanteCSV csv;
        String previousCodeCIS = null;
        List<InfoImportanteCSV> infos = new ArrayList<>();
        while (it.hasNext()) {
            csv = it.next();

            if (previousCodeCIS != null && !csv.getCodeCIS().equals(previousCodeCIS)) {
                infosImportantes.addAll(handleInfos(infos, medicaments));

                infos = new ArrayList<>();
            }

            infos.add(csv);
            previousCodeCIS = csv.getCodeCIS();
        }

        if (previousCodeCIS != null) {
            handleInfos(infos, medicaments);
        }

        return infosImportantes;
    }

    private List<InfoImportante> handleInfos(List<InfoImportanteCSV> infos, List<Medicament> medicaments) {
        List<InfoImportante> infosImportantes = new ArrayList<>();
        Medicament medicament;

        if (infos.size() == 0) {
            return infosImportantes;
        } else {
            medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals(infos.get(0).getCodeCIS())).findFirst().orElse(null);
        }

        if (medicament == null) {
            return infosImportantes;
        }

        infos.sort((o1, o2) -> o2.getDateDebut().compareTo(o1.getDateDebut()));


        InfoImportante infoImportante;
        Medicament.InfoImportante infoImportanteBis;
        Element link;
        for (InfoImportanteCSV info : infos) {
            infoImportante = new InfoImportante();
            infoImportante.setCodeCIS(info.getCodeCIS());
            infoImportante.setDateDebut(info.getDateDebut());
            infoImportante.setDateFin(info.getDateFin());
            link = Jsoup.parse(info.getInfo()).getElementsByTag("a").get(0);
            infoImportante.setInfoURL(link.attributes().get("href"));
            infoImportante.setInfoLibelle(link.text());
            infosImportantes.add(infoImportante);

            infoImportanteBis = new Medicament.InfoImportante();
            infoImportanteBis.setDateDebut(infoImportante.getDateDebut());
            infoImportanteBis.setDateFin(infoImportante.getDateFin());
            infoImportanteBis.setInfoURL(infoImportante.getInfoURL());
            infoImportanteBis.setInfoLibelle(infoImportante.getInfoLibelle());
            medicament.getInfosImportantes().add(infoImportanteBis);
        }

        return infosImportantes;
    }

}
