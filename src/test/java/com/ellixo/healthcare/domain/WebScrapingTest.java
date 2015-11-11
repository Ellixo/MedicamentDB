package com.ellixo.healthcare.domain;

import com.ellixo.healthcare.services.MedicamentService;
import com.google.common.base.Strings;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

public class WebScrapingTest {

    @Test
    public void scrap() throws IOException {
        // 64460520 60730566 63357706 66391261 60730566 60558554
        Document doc = Jsoup.connect("http://base-donnees-publique.medicaments.gouv.fr/extrait.php?specid=69995434").get();
        Elements elements = doc.select("h2.ficheInfo:contains(Indications thérapeutiques)");
        if (elements.size() != 0) {
            Element start = elements.first();
            Element end = doc.select("h2:gt(" + start.elementSiblingIndex() + ")").first();
            elements = doc.select("p:gt(" + start.elementSiblingIndex() + "):lt(" + end.elementSiblingIndex() + ")");

            StringBuilder sb = new StringBuilder();
            MedicamentService.MutableInteger listeCount = new MedicamentService.MutableInteger(0);
            elements.forEach(
                    x -> {
                        x.childNodes().forEach(
                                y -> {
                                    String string = getString(y, null);
                                    if (!Strings.isNullOrEmpty(string)) {
                                        String classParent = y.parentNode().attributes().get("class");
                                        if (classParent != null && classParent.startsWith("AmmListePuces")) {
                                            int length = "AmmListePuces".length();
                                            int index = Integer.parseInt(classParent.substring(length, length + 1));
                                            if (listeCount.getValue() > index) {
                                                sb.append("</ul>");
                                            } else if (listeCount.getValue() < index) {
                                                sb.append("<ul>");
                                            }
                                            listeCount.setValue(index);

                                            sb.append("<li>" + string + "</li>");
                                        } else {
                                            if (listeCount.getValue() != 0) {
                                                sb.append("</ul>");
                                            }
                                            listeCount.setValue(0);
                                            sb.append(string + " ");
                                        }
                                    }
                                });

                        if (sb.length() != 0) {
                            if (!sb.toString().endsWith(">")) {
                                sb.append("<br>");
                            }
                        }
                    }
            );
            for (int i = 0; i < listeCount.getValue(); i++) {
                sb.append("</ul>");
            }
            String text = sb.toString();

            if (text.endsWith("<br>")) {
                text = text.substring(0, text.length() - 4);
            }

            System.out.println(text);
        }
    }

    @Test
    public void scrapResume() throws IOException {
        // 64460520 60730566 63357706 66391261 60558554 64073783 62607530 69723039
        Document doc = Jsoup.connect("http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=62607530&typedoc=R").get();
        Elements elements = doc.select("a[name=RcpIndicTherap]");
        if (elements.size() != 0) {
            Element start = elements.first().parent();
            Element end = doc.select("*." + start.attributes().get("class") + ":gt(" + start.elementSiblingIndex() + ")").first();
            elements = doc.select("p:gt(" + start.elementSiblingIndex() + "):lt(" + end.elementSiblingIndex() + ")");

            StringBuilder sb = new StringBuilder();
            MedicamentService.MutableInteger listeCount = new MedicamentService.MutableInteger(0);
            elements.forEach(
                    x -> {
                        String css = x.attr("class");
                        if (css != null && css.startsWith("AmmListePuces")) {
                            int length = "AmmListePuces".length();
                            int index = 1;
                            if (css.length() > length) {
                                index = Integer.parseInt(css.substring(length, length + 1));
                            }
                            if (listeCount.getValue() > index) {
                                sb.append("</ul>");
                            } else if (listeCount.getValue() < index) {
                                sb.append("<ul>");
                            }
                            listeCount.setValue(index);
                        } else {
                            if (listeCount.getValue() != 0) {
                                sb.append("</ul>");
                            } else {
                                if (sb.length() != 0) {
                                    sb.append("<br>");
                                }
                            }
                            listeCount.setValue(0);
                        }

                        MutableBoolean isList = new MutableBoolean(false);
                        x.childNodes().forEach(
                                y -> {
                                    String string = getString(y, "http://base-donnees-publique.medicaments.gouv.fr/affichageDoc.php?specid=69995434&typedoc=R");
                                    if (!Strings.isNullOrEmpty(string)) {
                                        if (css != null && css.startsWith("AmmAnnexeTitre")) {
                                            string = "<b>" + string + "</b>";
                                        } else if (css != null && css.startsWith("AmmListePuces") && isList.isFalse()) {
                                            string = "<li>" + string;
                                            isList.setValue(true);
                                        }

                                        sb.append(string + " ");
                                    }
                                });
                        if (isList.isTrue()) {
                            sb.append("</li>");
                        }
                    }
            );

            for (int i = 0; i < listeCount.getValue(); i++) {
                sb.append("</ul>");
            }
            String text = sb.toString();

            if (text.endsWith("<br>")) {
                text = text.substring(0, text.length() - 4);
            }

            System.out.println(text);
        }
    }

    private String formatLinks(String text, String url) {
        text = text.replace("href=\"#", "href=\"" + url + "#");
        text = text.replace("href=\"/", "href=\"" + url + "/");
        return text;
    }

    private String getString(Node node, String link) {
        String string = node.toString().trim();
        if (string.length() > 1 || string.equals(":")) {
            if (node.nodeName().equals("a") && Strings.isNullOrEmpty(node.attributes().get("href"))) {
                StringBuilder sb = new StringBuilder();
                String tmp;
                for (Node child : node.childNodes()) {
                    tmp = getString(child, link);
                    if (tmp != null) {
                        sb.append(getString(child, link));
                    }
                }
                return sb.toString();
            } else if (node.nodeName().equals("a")) {
                StringBuilder sb = new StringBuilder();
                String tmp;
                for (Node child : node.childNodes()) {
                    tmp = getString(child, link);
                    if (tmp != null) {
                        sb.append(getString(child, link));
                    }
                }

                String href = node.attributes().get("href");
                if (!href.startsWith("http")) {
                    href = link + href;
                }

                return "<a href=\"" + href + "\">" + sb.toString() + "</a>";
            } else if (node.nodeName().equals("span")) {
                StringBuilder sb = new StringBuilder();
                String tmp;
                for (Node child : node.childNodes()) {
                    tmp = getString(child, link);
                    if (tmp != null) {
                        sb.append(getString(child, link));
                    }
                }
                string = sb.toString();

                if (Strings.isNullOrEmpty(string)) {
                    return null;
                }

                String css = node.attributes().get("class");

                boolean gras = css.contains("gras");
                boolean souligne = css.contains("souligne");
                boolean italique = css.contains("italique");

                sb = new StringBuilder();

                if (gras) {
                    sb.append("<b>");
                }
                if (souligne) {
                    sb.append("<u>");
                }
                if (italique) {
                    sb.append("<i>");
                }

                sb.append(string);

                if (italique) {
                    sb.append("</i>");
                }
                if (souligne) {
                    sb.append("</u>");
                }
                if (gras) {
                    sb.append("</b>");
                }

                return sb.toString();
            } else {
                if (string.endsWith("·")) {
                    return null;
                } else {
                    return string;
                }
            }
        }
        return null;
    }

}
