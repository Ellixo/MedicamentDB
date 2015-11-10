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

package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.Application;
import com.ellixo.healthcare.services.MedicamentService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Ignore
public class MedicamentTest {

    @Autowired
    MedicamentService service;

    @Test
    public void readMedicaments() {
        File dir = new File(MedicamentTest.class.getClassLoader().getResource(".").getFile());

        List<Medicament> medicaments = service.readMedicaments(dir).getLeft();

        assertEquals(medicaments.size(), 7);
        assertEquals(medicaments.get(0).getCodeCIS(), "61266250");
        assertEquals(medicaments.get(0).getDenomination(), "A 313 200 000 UI POUR CENT, pommade");
        assertEquals(medicaments.get(0).getDateAMM(), Date.from(LocalDate.of(1998, 3, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Medicament medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals("60008845")).findFirst().get();

        assertEquals(medicament.getCompositions().get(0).getSubstancesActives().size(), 1);
        assertEquals(medicament.getCompositions().get(0).getSubstancesActives().get(0).getFractionsTherapeutiques().size(), 1);

        medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals("62869109")).findFirst().get();

        assertEquals(medicament.getAvisSMR().size(), 1);
        assertEquals(medicament.getConditionsPrescriptionDelivrance().size(), 1);

        medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals("61872344")).findFirst().get();

        assertEquals(medicament.getInfosGenerique().getCodeGroupe(), "1");
        assertEquals(medicament.getInfosGenerique().getLibelleGroupe(), "CIMETIDINE 200 mg - TAGAMET 200 mg, comprimé pelliculé");
        assertEquals(medicament.getInfosGenerique().getType(), TypeGenerique.GENERIQUE);
        assertEquals(medicament.getInfosGenerique().getAutresMedicamentsGroupe().get(0).getCodeCIS(), "67535309");
    }

}
