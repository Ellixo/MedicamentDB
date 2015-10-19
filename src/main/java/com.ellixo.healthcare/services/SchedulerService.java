package com.ellixo.healthcare.services;

import com.ellixo.healthcare.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Profile({"prod","docker"})
public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    @Value("${db.download.url}")
    private String downloadURL;

    @Autowired
    private MedicamentService medicamentService;

    @PostConstruct
    public void init() {
        updateDB();
    }

    @Scheduled(cron = "0 3 * * *")
    public void updateDB() {
        try {
            Path dir = Files.createTempDirectory("MedicamentDB");

            downloadFile(dir, Constants.CIS_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.CIS_CIP_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.CIS_COMPO_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.CIS_HAS_SMR_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.CIS_HAS_ASMR_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.HAS_LIENS_PAGE_CT_BDPM_FILE, TypePrefix.CT);
            downloadFile(dir, Constants.CIS_CPD_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadFile(dir, Constants.CIS_GENER_BDPM_FILE, TypePrefix.DIGIT);
            downloadFile(dir, Constants.CIS_INFO_IMPORTANTES_FILE, TypePrefix.CODE_CIS);

            medicamentService.updateDB(dir.toFile());
        } catch (IOException e) {
            LOG.error("updateDB Error", e);
        }
    }

    private void downloadFile(Path dir, String file, TypePrefix type) throws IOException {
        URL url = new URL(downloadURL + "?fichier=" + file);

        LOG.info("Download DB File {}", url);

        downloadFile(url, new File(dir.toFile(), file), type);
    }

    private void downloadFile(URL url, File file, TypePrefix type) throws IOException {
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "windows-1252"))) {
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
                    String line = null;
                    String tmp;
                    boolean lineStart = false;
                    while ((tmp = reader.readLine()) != null) {
                        switch (type) {
                            case CODE_CIS:
                                lineStart = tmp.length() > 8 && isInteger(tmp.substring(0, 8));
                                break;
                            case CT:
                                lineStart = tmp.startsWith("CT");
                                break;
                            case DIGIT:
                                lineStart = Character.isDigit(tmp.charAt(0));
                                break;
                        }

                        if (lineStart) {
                            if (line != null) {
                                writer.write(line + Constants.LINE_SEPARATOR);
                            }
                            line = tmp;
                        } else {
                            line = line + tmp;
                        }
                    }

                    if (line != null) {
                        writer.write(line + Constants.LINE_SEPARATOR);
                    }
                }
            } finally {
                httpConn.disconnect();
            }
        }
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static enum TypePrefix {
        CODE_CIS, CT, DIGIT
    }
}