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
public class MedicamentManualTest {

    @Autowired
    MedicamentService service;

    @Test
    public void readMedicaments() {
        File dir = new File("/Users/Greg/Projects/MedicamentDB/src/main/resources/dataset");

        List<Medicament> medicaments = service.readMedicaments(dir).getLeft();

        medicaments.forEach(x -> {
            int count = 0;
            if (x.getPresentations().size() == 0) {
                System.out.println(x.getCodeCIS());
            }
        });
    }

}
