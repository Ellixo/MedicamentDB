package com.ellixo.healthcare.web;

import com.ellixo.healthcare.domain.EMail;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @RequestMapping(value = "/tools/email", method = RequestMethod.POST)
    public ResponseEntity emailSubmit(@RequestBody EMail emailInfo) {
        LOG.info("Contact Email : " + emailInfo);

        if (emailInfo.getToken() == null || !emailInfo.getToken().equals("openmedicaments")) {
            return new ResponseEntity(HttpStatus.OK);
        }

        SendGrid sendgrid = new SendGrid("KEY");

        SendGrid.Email email = new SendGrid.Email();

        email.addTo("info@ellixo.com");
        email.setFrom(emailInfo.getEmail());
        email.setSubject("Contact - Open Medicaments");
        email.setHtml("<p>Nom : " + emailInfo.getName() + "</p>" +
                "<p>Téléphone : " + emailInfo.getPhone() + "</p>" +
                "<br>" +
                "<p>" + emailInfo.getMessage() + "</p>");

        try {
            sendgrid.send(email);
        } catch (SendGridException e) {
            LOG.error("Email Error", e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
