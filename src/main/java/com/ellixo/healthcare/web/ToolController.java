package com.ellixo.healthcare.web;

import com.ellixo.healthcare.domain.EMail;
import com.ellixo.healthcare.services.MedicamentService;
import com.sendgrid.SendGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ToolController {

    private static final Logger LOG = LoggerFactory.getLogger(ToolController.class);

    private static final String KEY_FILE_PATH = "key.file.path";

    @Autowired
    private MedicamentService service;

    @RequestMapping(value = "/tools/email", method = RequestMethod.POST)
    public ResponseEntity emailSubmit(@RequestBody EMail emailInfo) {
        LOG.info("Contact Email : " + emailInfo);

        if (emailInfo.getToken() == null || !emailInfo.getToken().equals("openmedicaments")) {
            return new ResponseEntity(HttpStatus.OK);
        }

        try {
            String keyFilePath = System.getProperty(KEY_FILE_PATH);
            String key = new String(Files.readAllBytes(Paths.get(keyFilePath)));
            SendGrid sendgrid = new SendGrid(key);

            SendGrid.Email email = new SendGrid.Email();

            email.addTo("info@ellixo.com");
            email.setFrom(emailInfo.getEmail());
            email.setSubject("Contact - Open Medicaments");
            email.setHtml("<p>Nom : " + emailInfo.getName() + "</p>" +
                    "<p>Téléphone : " + emailInfo.getPhone() + "</p>" +
                    "<br>" +
                    "<p>" + emailInfo.getMessage() + "</p>");


            sendgrid.send(email);
        } catch (Exception e) {
            LOG.error("Email Error", e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/medicaments-sitemap", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> sitemap() {
        return new ResponseEntity(service.getSitemap(), HttpStatus.OK);
    }

}
