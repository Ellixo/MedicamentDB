package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.Application;
import com.ellixo.healthcare.services.MedicamentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
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

        assertEquals(medicament.getComposition().get(0).getFractionsTherapeutiques().size(), 1);
        assertEquals(medicament.getComposition().get(0).getFractionsTherapeutiques().size(), 1);

        medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals("62869109")).findFirst().get();

        assertEquals(medicament.getAvisSMR().size(), 1);
        assertEquals(medicament.getConditionsPrescriptionDelivranceCSV().size(), 1);

        medicament = medicaments.stream().filter(x -> x.getCodeCIS().equals("61872344")).findFirst().get();

        assertEquals(medicament.getInfosGenerique().getCodeGroupe(), "1");
        assertEquals(medicament.getInfosGenerique().getLibelleGroupe(), "CIMETIDINE 200 mg - TAGAMET 200 mg, comprimé pelliculé");
        assertEquals(medicament.getInfosGenerique().getType(), TypeGenerique.GENERIQUE);
        assertEquals(medicament.getInfosGenerique().getAutresMedicamentsGroupe().get(0).getCodeCIS(), "67535309");
    }

}
