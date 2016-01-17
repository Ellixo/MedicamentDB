package com.ellixo.healthcare.services;

import com.ellixo.healthcare.domain.Interaction;
import com.ellixo.healthcare.domain.MedicamentInteraction;
import com.ellixo.healthcare.domain.SubstanceInteraction;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InteractionService {

    private static final Logger LOG = LoggerFactory.getLogger(InteractionService.class);

    public MedicamentInteraction readInteractionMedicamenteuses(File dir) throws IOException {
        List<SubstanceInteraction> thesaurus = readThesaurus(dir);
        Map<String, List<Interaction>> interactions = readInteractions(dir);

        for (SubstanceInteraction substanceInteraction : thesaurus) {

            for (String famille : substanceInteraction.getFamilles()) {
                famille = normalize(famille, true);

                // cas particuliers
                switch (famille) {
                    case "racecadodril":
                        famille = "racecadotril";
                        break;
                }

                List<Interaction> interaction = interactions.get(famille);
                if (interaction == null) {
                    LOG.warn("famille " + substanceInteraction.getSubstance() + " inconnue");
                } else {
                    substanceInteraction.getInteractions().addAll(interaction.stream().map(x -> x.getId()).collect(Collectors.toList()));
                }
            }
        }

        return new MedicamentInteraction(thesaurus, interactions.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList()));
    }

    private List<SubstanceInteraction> readThesaurus(File dir) throws IOException {
        File file = new File(dir, "thesaurus.pdf");

        PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        ThesaurusPDFStripper pdfStripper = new ThesaurusPDFStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        pdfStripper.setStartPage(2);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        pdfStripper.getText(pdDoc);

        cosDoc.close();

        return pdfStripper.substances;
    }

    private Map<String, List<Interaction>> readInteractions(File dir) throws IOException {
        File file = new File(dir, "interactions.pdf");

        PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));

        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        InteractionPDFStripper pdfStripper = new InteractionPDFStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        pdfStripper.setStartPage(2);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        pdfStripper.getText(pdDoc);

        cosDoc.close();

        Map<String, List<Interaction>> interactions = pdfStripper.interactions;
        Map<String, List<Interaction>> newInteractions = new HashMap<>();

        for (List<Interaction> tmp : interactions.values()) {
            for (Interaction interaction : tmp) {
                String famille2 = normalize(interaction.getFamille2(), true);

                // cas particuliers
                switch (famille2) {
                    case "medicaments hyponatremiants":
                        famille2 = "hyponatremiants";
                        break;
                }

                List<Interaction> interactions2 = interactions.get(famille2);
                if (interactions2 == null) {
                    LOG.warn("interaction " + interaction.getFamille2() + " inconnu");

                    Interaction newInteraction;
                    if (newInteractions.get(famille2) == null) {
                        newInteractions.put(famille2, new ArrayList<>());

                        newInteraction = pdfStripper.createEmptyInteraction(-1, interaction.getFamille2());
                    } else {
                        newInteraction = pdfStripper.createEmptyInteraction(Integer.parseInt(newInteractions.get(famille2).get(0).getId1()), interaction.getFamille2());
                    }

                    newInteraction.setId2(interaction.getId1());
                    newInteraction.setFamille2(interaction.getFamille1());
                    newInteraction.setDescription(interaction.getDescription());
                    newInteraction.setConseil(interaction.getConseil());

                    newInteractions.get(famille2).add(newInteraction);

                    interaction.setId2(newInteraction.getId1());
                } else {
                    interaction.setId2(interactions2.get(0).getId1());
                }
            }
        }

        for (String key : newInteractions.keySet()) {
            interactions.put(key, newInteractions.get(key));
        }

        return interactions;

    }

    private static String normalize(String string, boolean removeAutres) {
        if (string == null) {
            return string;
        }

        string = Normalizer.normalize(string, Normalizer.Form.NFD).toLowerCase().trim().replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        if (removeAutres) {
            if (string.startsWith("autres ")) {
                string = string.substring("autres ".length()).trim();
            }
            if (string.endsWith(" (autres)")) {
                string = string.substring(0, string.indexOf(" (autres)"));
            }
        }

        return string;
    }

    private static class ThesaurusPDFStripper extends PDFTextStripper {

        private SubstanceInteraction substance = null;
        private List<SubstanceInteraction> substances = new ArrayList<>();

        public ThesaurusPDFStripper() throws IOException {
        }

        protected void writePage() throws IOException {
            TextPosition previousCharacter = null;
            String currentLine = new String();

            for (Iterator i$ = this.charactersByArticle.iterator(); i$.hasNext(); this.endArticle()) {
                List textList = (List) i$.next();

                for (Object o : textList) {
                    TextPosition character = (TextPosition) o;

                    int fontSize = (int) (character.getFontSizeInPt() / character.getFontSize());
                    int previousFontSize = fontSize;
                    if (previousCharacter != null) {
                        previousFontSize = (int) (previousCharacter.getFontSizeInPt() / previousCharacter.getFontSize());
                    }

                    if (fontSize != previousFontSize) {
                        currentLine = currentLine.trim().toLowerCase();

                        if (previousFontSize >= 7) {
                            // substance
                            switch (currentLine) {
                                case "abciximab (c 7e3b fab)":
                                    currentLine = "abciximab (c7e3b fab)";
                                    break;
                            }

                            substance = new SubstanceInteraction();
                            substance.setSubstance(currentLine);

                            substances.add(substance);
                        } else {
                            // interaction
                            if (currentLine.contains("interactions en propre")) {
                                substance.getFamilles().add(substance.getSubstance());
                            }

                            int index = currentLine.indexOf(":");
                            if (index > 0) {
                                currentLine = currentLine.substring(index + 1);
                                String[] split = currentLine.split(" \\- ");
                                for (String tmp : split) {
                                    tmp = tmp.trim();
                                    if (tmp.length() > 1) {
                                        substance.getFamilles().add(tmp);
                                    }
                                }
                            }
                        }

                        currentLine = new String();
                    }

                    currentLine += character.getUnicode();

                    previousCharacter = character;
                }
            }
        }
    }

    private static class InteractionPDFStripper extends PDFTextStripper {

        private int indexId = 0;
        private int indexId1 = 0;
        private String famille1;

        private Interaction interaction = null;
        private Map<String, List<Interaction>> interactions = new HashMap<>();

        public InteractionPDFStripper() throws IOException {
        }

        protected void writePage() throws IOException {
            TextPosition previousCharacter = null;
            String currentLine = new String();
            String currentLine2 = new String();
            boolean columnsInProgress = false;
            float firstY = -1;

            for (Iterator i$ = this.charactersByArticle.iterator(); i$.hasNext(); this.endArticle()) {
                List textList = (List) i$.next();

                for (Object o : textList) {
                    TextPosition character = (TextPosition) o;

                    // on zappe le numero de page
                    if (firstY < 0 || Math.abs(firstY - character.getY()) < 5) {
                        firstY = character.getY();
                        continue;
                    }

                    int fontSize = (int) (character.getFontSizeInPt() / character.getFontSize());
                    int previousFontSize = fontSize;
                    if (previousCharacter != null) {
                        previousFontSize = (int) (previousCharacter.getFontSizeInPt() / previousCharacter.getFontSize());
                    }

                    if (fontSize != previousFontSize) {
                        currentLine = currentLine.trim().toLowerCase();
                        currentLine2 = currentLine2.trim().toLowerCase();

                        if (previousFontSize >= 9) {
                            ++indexId1;
                            famille1 = currentLine.trim();

                            interactions.put(normalize(famille1, true), new ArrayList<>());

                            interaction = null;
                        } else if (previousFontSize >= 7) {
                            ++indexId;

                            interaction = new Interaction();
                            interaction.setId(Integer.toString(indexId));
                            interaction.setId1(Integer.toString(indexId1));
                            interaction.setFamille1(famille1);
                            String famille2 = currentLine.substring(1).trim();

                            interaction.setFamille2(famille2);

                            interactions.get(normalize(famille1, true)).add(interaction);

                            columnsInProgress = true;
                        } else if (interaction != null) {
                            interaction.setDescription(currentLine);
                            int indexConseil = currentLine2.indexOf("\n");
                            if (indexConseil > 0) {
                                interaction.setType(currentLine2.substring(0, indexConseil));
                                if (indexConseil + 1 < currentLine2.length()) {
                                    interaction.setConseil(currentLine2.substring(indexConseil + 1));
                                }
                            } else {
                                interaction.setType(currentLine2);
                            }
                            columnsInProgress = false;
                        }

                        currentLine = new String();
                        currentLine2 = new String();
                    }

                    if (previousCharacter != null && Math.abs(character.getY() - previousCharacter.getY()) > 10) {
                        if (currentLine2.length() != 0 && columnsInProgress && character.getX() > 315) {
                            currentLine2 += "\n";
                        } else if (currentLine.length() != 0) {
                            currentLine += "\n";
                        }
                    }

                    if (columnsInProgress && character.getX() > 315) {
                        currentLine2 += character.getUnicode();
                    } else {
                        currentLine += character.getUnicode();
                    }

                    previousCharacter = character;
                }
            }
        }

        public Interaction createEmptyInteraction(int id, String famille) {
            interaction = new Interaction();
            interaction.setId(Integer.toString(++indexId));
            interaction.setId1(Integer.toString(id < 0 ? ++indexId1 : id));
            interaction.setFamille1(famille);

            interactions.get(normalize(famille1, true)).add(interaction);

            return interaction;
        }
    }

}
