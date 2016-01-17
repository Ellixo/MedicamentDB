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

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PDFTest {

    @Test
    public void parse() throws IOException {
        File file = new File(MedicamentTest.class.getClassLoader().getResource(".").getFile(), "thesaurus.pdf");

        PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        AdvancedPDFStripper pdfStripper = new AdvancedPDFStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        pdfStripper.setStartPage(2);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        pdfStripper.getText(pdDoc);

        System.out.println(pdfStripper.substances);
    }

    private static class AdvancedPDFStripper extends PDFTextStripper {

        private List<SubstanceInteraction> substances = new ArrayList<>();

        public AdvancedPDFStripper() throws IOException {
        }

        protected void writePage() throws IOException {
            TextPosition previousCharacter = null;
            String currentLine = new String();

            for (Iterator i$ = this.charactersByArticle.iterator(); i$.hasNext(); this.endArticle()) {
                List textList = (List) i$.next();

                for (Object o : textList) {
                    TextPosition character = (TextPosition) o;

                    if (previousCharacter != null && character.getFontSize() != previousCharacter.getFontSize()) {
                        currentLine = currentLine.trim().toLowerCase();

                        if (previousCharacter.getFontSize() > 7) {
                            // substance
                            SubstanceInteraction substance = new SubstanceInteraction();
                            substances.add(substance);

                            substance.setSubstance(currentLine);
                        } else {
                            // interaction
                            if (currentLine.contains("interactions en propre")) {
                                lastSubstance().getInteractions().add(lastSubstance().getSubstance());
                            }

                            int index = currentLine.indexOf(":");
                            if (index > 0) {
                                currentLine = currentLine.substring(index + 1);
                                String[] split = currentLine.split(" \\- ");
                                for (String tmp : split) {

                                    lastSubstance().getInteractions().add(tmp.trim());
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

        private SubstanceInteraction lastSubstance() {
            if (substances.size() == 0) {
                return null;
            }

            return substances.get((substances.size() - 1));
        }
    }

    public static class SubstanceInteraction {

        private String substance;
        private List<String> interactions = new ArrayList<>();

        public String getSubstance() {
            return substance;
        }

        public void setSubstance(String substance) {
            this.substance = substance;
        }

        public List<String> getInteractions() {
            return interactions;
        }

        public void setInteractions(List<String> interactions) {
            this.interactions = interactions;
        }
    }

}
