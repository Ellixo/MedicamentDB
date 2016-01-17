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

package com.ellixo.healthcare.services;

import com.ellixo.healthcare.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Profile({"prod", "docker"})
public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    @Value("${db.download.db.url}")
    private String downloadDBURL;
    @Value("${db.download.interactions.url}")
    private String downloadInteractionsURL;

    @Autowired
    private MedicamentService medicamentService;

    @PostConstruct
    public void init() {
        Runnable task = () -> {
            updateDB();
        };
        new Thread(task).start();
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void updateDB() {
        try {
            Path dir = Files.createTempDirectory("MedicamentDB");

            downloadDBFile(dir, Constants.CIS_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.CIS_CIP_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.CIS_COMPO_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.CIS_HAS_SMR_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.CIS_HAS_ASMR_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.HAS_LIENS_PAGE_CT_BDPM_FILE, TypePrefix.CT);
            downloadDBFile(dir, Constants.CIS_CPD_BDPM_FILE, TypePrefix.CODE_CIS);
            downloadDBFile(dir, Constants.CIS_GENER_BDPM_FILE, TypePrefix.DIGIT);
            downloadDBFile(dir, Constants.CIS_INFO_IMPORTANTES_FILE, TypePrefix.CODE_CIS);

            downloadInteractions(dir);

            medicamentService.updateDB(dir.toFile());
        } catch (IOException e) {
            LOG.error("updateDB Error", e);
        }
    }

    private void downloadDBFile(Path dir, String file, TypePrefix type) throws IOException {
        URL url = new URL(downloadDBURL + "?fichier=" + file);

        LOG.info("Download DB File {}", url);

        downloadDBFile(url, new File(dir.toFile(), file), type);
    }

    private void downloadDBFile(URL url, File file, TypePrefix type) throws IOException {
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

    private void downloadInteractions(Path dir) throws IOException {
        LOG.info("Download Interactions Files");

        Document doc = Jsoup.connect(downloadInteractionsURL).get();

        Elements elements = doc.select("a:contains(Thesaurus)");
        if (elements.size() == 0) {
            LOG.error("cannot download interactions");
        } else {
            elements.forEach(x -> {
                if (x.toString().contains("Référentiel national des interactions médicamenteuses")) {
                    downloadInteraction(dir, "interactions.pdf", x.attr("href"));
                } else if (x.toString().contains("Index des substances")) {
                    downloadInteraction(dir, "thesaurus.pdf", x.attr("href"));
                }
            });
        }
    }

    private void downloadInteraction(Path dir, String file, String urlStr) {
        try {
            URL urlRoot = new URL(downloadInteractionsURL);

            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(urlRoot.getProtocol() + "://" + urlRoot.getHost());
            urlBuilder.path(urlStr);

            URL url = new URL(urlBuilder.toUriString());

            try (ReadableByteChannel rbc = Channels.newChannel(url.openStream())) {
                try (FileOutputStream fos = new FileOutputStream(new File(dir.toFile(), file))) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private enum TypePrefix {
        CODE_CIS, CT, DIGIT
    }
}