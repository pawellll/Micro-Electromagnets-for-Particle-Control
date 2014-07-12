/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr;

import Platform.CrossBitmap;
import Platform.CrossRes;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.ThresholdGlobal;

/**
 * @author Maciek
 */
public class OCRPlugin {

    private final List<List<List<OCRCharacter>>> recSet; // Zbiór wejściowy
    private final List<OCRCharacter> testSet; // Zbiór testowy
    private final CrossBitmap imageIn; // Obraz wejściowy
    private String recognizedText; // Rozpoznany tekst

    /**
     * Konstruktor przyjmujący obraz wejściowy, z którego będzie rozpoznawany
     * tekst
     *
     * @param imageIn Obraz wejściowy
     */
    public OCRPlugin(CrossBitmap img) {
//        CrossBitmap img = preprocess(imageIn);
        this.imageIn = img;
        this.recSet = Segment.getSegments(img);
        this.testSet = new ArrayList<>();
        this.createTestSet("./fontsamples");
    }

    /**
     * Funkcja zwracająca rozpoznany tekst.
     *
     * @return Zwraca rozpoznany tekst
     */
    public String getText() {
        return recognizedText;
    }

    /**
     * Metoda tworzy zbiór testowy na podstawie plików testowych zapisanych w
     * zdefiniowanym katalogu. (Tu: ./res/Font Sample/)
     *
     * @param directory katalog obrazów testowych
     */
    final public void createTestSet(String dirpath) {
        File directory = new File(dirpath);
        if (directory.isDirectory()) {
            for (File testImageFile : directory.listFiles()) {
                String filepath = testImageFile.getAbsolutePath();

                if (filepath.matches("^.*\\.(jpg|JPG|gif|GIF|png|PNG|jpeg|JPEG)$")) {
                    CrossBitmap fileImage = CrossRes.loadImg(filepath);
                    char testChar = (char) Integer.parseInt(testImageFile.getName().substring(0, 2), 16);

                    testSet.add(new OCRCharacter(fileImage, testChar));
                }
            }
        }
    }

    /**
     * Funkcja klasyfikuje obraz jako konkretny znak posługując się porównaniem
     * wektora cech danego obrazu z wektorami cech obrazów testowych.
     *
     * @param recChar obiekt typu OCRCharacter zawierający obraz rozpoznawanego
     * znaku
     * @return zwraca rozpoznany znak
     */
    private char recognizeChar(OCRCharacter recChar) {
        List<MetricsToChar> classificationArray = new ArrayList<>();

        for (OCRCharacter testChar : testSet) {
            double metric = 0;
            for (int i = 0; i < recChar.features.size(); i++) {
                double addValue = Math.sqrt((recChar.features.get(i)
                        * recChar.features.get(i)) + (testChar.features.get(i)
                        * testChar.features.get(i)));

                metric += addValue;
            }

            classificationArray.add(new MetricsToChar(metric, testChar.getChar()));
        }

        Collections.sort(classificationArray);
        return classificationArray.get(0).getChar();
    }

    /**
     * Metoda przeprowadza klasyfikacje na wszystkich elementach zbioru
     * wejściowego, po czym składa gotowy rozpoznany tekst.
     */
    public void extractText() {
        int lp = 0;
        StringBuilder outString = new StringBuilder();
        for (List<List<OCRCharacter>> listListChar : recSet) {
            for (List<OCRCharacter> listChar : listListChar) {
                for (OCRCharacter recChar : listChar) {
//                    MarvinImageIO.saveImage(recChar.getImage(), "./res/Segmenty/" + lp++ + ".png");
                    char nextChar = recognizeChar(recChar);
                    outString.append(nextChar);
                }
                outString.append(" ");
            }
            outString.append("\n");
        }

        recognizedText = outString.toString();
    }

    /**
     * Metoda przetwarza wstępnie obraz: usuwa tło i binaryzuje w celu dalszego
     * przetwarzania
     *
     * @param imageIn obraz wejściowy
     * @return przetworzony wstępnie obraz
     */
    private CrossBitmap preprocess(CrossBitmap imageIn) {
        CrossBitmap imageOut = new CrossBitmap(new BufferedImage(imageIn.getWidth(), imageIn.getHeight(), BufferedImage.TYPE_INT_RGB));
        ThresholdGlobal binarizePlugin = new ThresholdGlobal();
        binarizePlugin.process(imageIn, imageOut);
        return imageOut;
    }
}
