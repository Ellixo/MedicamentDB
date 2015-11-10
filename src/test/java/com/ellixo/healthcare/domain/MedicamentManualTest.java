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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Ignore
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
