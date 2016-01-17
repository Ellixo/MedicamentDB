package com.ellixo.healthcare.domain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MedicamentInteraction {

    private static final Logger LOG = LoggerFactory.getLogger(MedicamentInteraction.class);

    private List<SubstanceInteraction> substances;
    private List<Interaction> interactions;

    public MedicamentInteraction(List<SubstanceInteraction> substances, List<Interaction> interactions) {
        this.substances = substances;
        this.interactions = interactions;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void updateInteractions(Medicament medicament) {
        for (Composition composition : medicament.getCompositions()) {
            for (SubstanceActive sa : composition.getSubstancesActives()) {
                List<Interaction> interactions = getInteractions(medicament, sa.getDenominationSubstance());
                if (interactions != null) {
                    for (Interaction interaction : interactions) {
                        sa.getInteractions().add(new Substance.Interaction(interaction.getId(), interaction.getId1(), interaction.getFamille1(), interaction.getId2(), interaction.getFamille2()));
                    }
                }

                for (FractionTherapeutique ft : sa.getFractionsTherapeutiques()) {
                    interactions = getInteractions(medicament, ft.getDenominationSubstance());
                    if (interactions != null) {
                        for (Interaction interaction : interactions) {
                            ft.getInteractions().add(new Substance.Interaction(interaction.getId(), interaction.getId1(), interaction.getFamille1(), interaction.getId2(), interaction.getFamille2()));
                        }
                    }
                }
            }
        }
    }

    private List<Interaction> getInteractions(Medicament medicament, String substance) {
        String molecule = Normalizer.normalize(substance, Normalizer.Form.NFD).toLowerCase().trim().replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        List<String> familles = substances.stream().filter(x -> contains(molecule, x.getSubstance())).map(x -> x.getInteractions()).flatMap(l -> l.stream()).collect(Collectors.toList());

        Set<Interaction> interactions = new HashSet<>();
        for (String famille : familles) {
            interactions.addAll(this.interactions.stream().filter(x -> famille.equals(x.getId())).collect(Collectors.toList()));
        }

        // cas particuliers
        if (medicament.getVoiesAdministration().contains("vaginale")) {
            interactions.addAll(this.interactions.stream().filter(x -> x.getFamille1().equals("médicaments utilisés par voie vaginale")).collect(Collectors.toList()));
        }
        if (medicament.getVoiesAdministration().contains("orale")) {
            interactions.addAll(this.interactions.stream().filter(x -> x.getFamille1().equals("médicaments administrés par voie orale")).collect(Collectors.toList()));
        }
        if (contains(molecule, "fer") && (medicament.getVoiesAdministration().contains("intraveineuse/voie extracorporelle"))) {
            interactions.addAll(this.interactions.stream().filter(x -> x.getFamille1().equals("interaction sels de fer par voie injectable")).collect(Collectors.toList()));
        }
        if (contains(molecule, "alcool") || contains(molecule, "alcools")) {
            interactions.addAll(this.interactions.stream().filter(x -> x.getFamille1().equals("alcool (boisson ou excipient)")).collect(Collectors.toList()));
        }

        if (interactions.size() == 0) {
            LOG.warn(medicament.getDenomination() + " - " + molecule + " (" + (familles.size() == 0 ? "pas de famille" : "familles " + StringUtils.join(familles, "-")) + ") : aucune interaction medicamenteuse");
            return null;
        } else {
            return new ArrayList<>(interactions);
        }
    }

    private boolean contains(String string, String part) {
        boolean ok = false;
        int index = string.toLowerCase().indexOf(part.toLowerCase());
        if (index >= 0) {
            index = index - 1;
            if (index < 0 || isSeparatorCharacter(string.charAt(index))) {
                index = index + part.length() + 1;
                ok = (index == string.length() || isSeparatorCharacter(string.charAt(index)));
            }
        }

        if (!ok) {
            // cas particulier
            if (part.startsWith("acide ")) {
                part = part.substring("acide ".length());
                return contains(string, part);
            }
        }

        return ok;
    }

    private boolean isSeparatorCharacter(char c) {
        return Character.isSpaceChar(c) || c == '(' || c == ')';
    }
}