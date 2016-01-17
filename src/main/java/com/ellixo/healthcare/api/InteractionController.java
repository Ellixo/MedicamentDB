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

package com.ellixo.healthcare.api;

import com.ellixo.healthcare.domain.Interaction;
import com.ellixo.healthcare.domain.InteractionEntreMedicaments;
import com.ellixo.healthcare.domain.Medicament;
import com.ellixo.healthcare.domain.Substance;
import com.ellixo.healthcare.domain.util.ESMapper;
import com.ellixo.healthcare.exception.UnknownObjectException;
import com.ellixo.healthcare.repository.InteractionRepository;
import com.ellixo.healthcare.repository.MedicamentRepository;
import com.ellixo.healthcare.services.MedicamentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/interactions")
@Api(value = "interactions", description = "Interactions Médicamenteuses", produces = "application/json", consumes = "application/json")
public class InteractionController {

    @Autowired
    private MedicamentService service;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private ESMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "interactions medicamenteuses", httpMethod = "GET", response = Medicament.class)
    public List<InteractionEntreMedicaments> get(@RequestParam(value = "ids") String ids) {
        String[] listIds = ids.split("\\|");

        List<InteractionEntreMedicaments> interactions = new ArrayList<>();

        List<Medicament> medicaments = new ArrayList<>();
        Medicament medicament;
        for (String id : listIds) {
            medicament = medicamentRepository.findOne(id);
            if (medicament == null) {
                throw new UnknownObjectException("medicament", id);
            }

            medicaments.add(medicament);
        }

        for (int i = 0; i < medicaments.size() - 1; i++) {
            for (int j = i + 1; j < medicaments.size(); j++) {
                interactions.addAll(generateInteractions(medicaments.get(i), medicaments.get(j)));
            }
        }

        return interactions;
    }

    private List<InteractionEntreMedicaments> generateInteractions(Medicament medicament1, Medicament medicament2) {
        Set<Substance.Interaction> interactionsTmp = new HashSet<>();
        List<InteractionEntreMedicaments> interactions = new ArrayList<>();

        Set<Substance.Interaction> interactions1 = medicament1.getInteractions();
        Set<Substance.Interaction> interactions2 = medicament2.getInteractions();

        for (Substance.Interaction interaction2 : interactions2) {
            interactionsTmp.addAll(interactions1.stream()
                    .filter(x -> x.getIdFamille2().equals(interaction2.getIdFamille1()))
                    .collect(Collectors.toSet()));
        }

        Interaction interaction;
        for (Substance.Interaction tmp : interactionsTmp) {
            interaction = interactionRepository.findOne(tmp.getId());
            interactions.add(new InteractionEntreMedicaments(medicament1.getCodeCIS(),
                    medicament1.getDenomination(),
                    medicament2.getCodeCIS(),
                    medicament2.getDenomination(),
                    interaction.getDescription(),
                    interaction.getType(),
                    interaction.getConseil()));
        }

        return interactions;
    }

}