package com.ellixo.healthcare.config;

import com.ellixo.healthcare.services.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
@Profile("dev")
public class DataSet {

    @Autowired
    private MedicamentService medicamentService;

    @PostConstruct
    public void init() {
        String dir = DataSet.class.getClassLoader().getResource("dataset").getFile();
        medicamentService.updateDB(new File(dir));
    }

}